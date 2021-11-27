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
     * @return LedgerTransaction if the transfer was successful, otherwise null.
     */
    public long execute(BigDecimal amount, int fromAccountNumber, int toAccountNumber) {

        ElectronicTransfer executedTransfer = null;
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

        try {
            BigDecimal fromBalance = fromAccountLedger.getBalance();
            fromAccountLedger.setBalance(fromBalance.subtract(executedTransfer.getAmount()));
            fromAccountLedger.setLastTransactionId(executedTransfer.getId());
            ledgerRepository.save(fromAccountLedger);

        } catch (Exception exception) {
            ledgerRepository.save(fromAccountLedgerPreviousState);
            ledgerTransactionRepository.delete(executedTransfer);
            return 0;
        }

        try {
            BigDecimal toBalance = toAccountLedger.getBalance();
            toAccountLedger.setBalance(toBalance.add(executedTransfer.getAmount()));
            toAccountLedger.setLastTransactionId(executedTransfer.getId());
            ledgerRepository.save(toAccountLedger);

        } catch (Exception exception) {
            ledgerRepository.save(toAccountLedgerPreviousState);
            ledgerRepository.save(fromAccountLedgerPreviousState);
            ledgerTransactionRepository.delete(executedTransfer);
            return 0;
        }
        return executedTransfer.getId();
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
