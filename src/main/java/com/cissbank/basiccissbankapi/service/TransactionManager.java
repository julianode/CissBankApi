package com.cissbank.basiccissbankapi.service;

import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;
import com.cissbank.basiccissbankapi.entity.ledger.AccountLedger;
import com.cissbank.basiccissbankapi.entity.ledger.ElectronicTransfer;
import com.cissbank.basiccissbankapi.entity.ledger.LedgerTransaction;
import com.cissbank.basiccissbankapi.repository.LedgerRepository;
import com.cissbank.basiccissbankapi.repository.LedgerTransactionRepository;

import java.math.BigDecimal;

public class TransactionManager {

    private final LedgerTransactionRepository ledgerTransactionRepository;
    private final LedgerRepository ledgerRepository;

    public TransactionManager(LedgerTransactionRepository ledgerTransactionRepository, LedgerRepository ledgerRepository) {
        this.ledgerTransactionRepository = ledgerTransactionRepository;
        this.ledgerRepository = ledgerRepository;
    }

    /**
     * Executes a transaction.
     * Rolls back to previous state if error in any step.
     * @return LedgerTransaction's id if the transfer was successful, otherwise zero.
     */
    public long executeLedgerTransaction(BigDecimal amount, int fromAccountNumber, int toAccountNumber) {

        ElectronicTransfer executedTransfer;
        AccountLedger fromAccountLedger;
        AccountLedger toAccountLedger;
        AccountLedger fromAccountLedgerPreviousState;
        AccountLedger toAccountLedgerPreviousState;

        try {
            fromAccountLedger = getAccountLedgerEligibleForTransfer(fromAccountNumber);
            toAccountLedger = getAccountLedgerEligibleForTransfer(toAccountNumber);
            fromAccountLedgerPreviousState = fromAccountLedger.clone();
            toAccountLedgerPreviousState = fromAccountLedger.clone();

        } catch (IllegalStateException | CloneNotSupportedException exception) {
            return 0;
        }

        ElectronicTransfer electronicTransfer = new ElectronicTransfer(amount, fromAccountNumber, toAccountNumber);
        try {
            executedTransfer = ledgerTransactionRepository.save(electronicTransfer);

        } catch (Exception exception) {
            return 0;
        }

        BigDecimal executedTransferAmount = executedTransfer.getAmount();
        long executedTransferId = executedTransfer.getId();

        try {
            updateFromAccountBalance(fromAccountLedger, executedTransferAmount, executedTransferId);
            updateToAccountBalance(toAccountLedger, executedTransferAmount, executedTransferId);

        } catch (Exception exception) {
            rollbackTransaction(fromAccountLedgerPreviousState, toAccountLedgerPreviousState, executedTransferId);
            return 0;
        }
        return executedTransferId;
    }

    private void updateToAccountBalance(AccountLedger toAccountLedger, BigDecimal executedTransferAmount, long executedTransferId) {
        BigDecimal toBalance = toAccountLedger.getBalance();
        toAccountLedger.setBalance(toBalance.add(executedTransferAmount));
        toAccountLedger.setLastTransactionId(executedTransferId);
        ledgerRepository.save(toAccountLedger);
    }

    private void updateFromAccountBalance(AccountLedger fromAccountLedger, BigDecimal executedTransferAmount, long executedTransferId) {
        BigDecimal fromBalance = fromAccountLedger.getBalance();
        fromAccountLedger.setBalance(fromBalance.subtract(executedTransferAmount));
        fromAccountLedger.setLastTransactionId(executedTransferId);
        ledgerRepository.save(fromAccountLedger);
    }

    private void rollbackTransaction(AccountLedger fromAccountLedgerPreviousState,
                                     AccountLedger toAccountLedgerPreviousState, long executedTransferId) {

        ledgerRepository.save(toAccountLedgerPreviousState);
        ledgerRepository.save(fromAccountLedgerPreviousState);
        ledgerTransactionRepository.deleteById(executedTransferId);
    }

    /**
     * @throws IllegalStateException in case account is not eligible for transference, or is not ACTIVE.
     */
    private AccountLedger getAccountLedgerEligibleForTransfer(int accountNumber) {

        AccountLedger accountLedger = ledgerRepository.findByOwnerAccountNumber(accountNumber);

        if (accountLedger.getStatus() != ActivationStatus.ACTIVE) {
            String message = String.format("Account is not ACTIVE and is not eligible for transference." +
                    " [accountNumber: %d]", accountNumber);
            throw new IllegalStateException(message);
        }
        return accountLedger;
    }
}
