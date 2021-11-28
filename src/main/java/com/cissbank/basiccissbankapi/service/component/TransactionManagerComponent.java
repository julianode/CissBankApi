package com.cissbank.basiccissbankapi.service.component;

import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;
import com.cissbank.basiccissbankapi.entity.ledger.AccountLedger;
import com.cissbank.basiccissbankapi.entity.ledger.ElectronicTransfer;
import com.cissbank.basiccissbankapi.repository.LedgerRepository;
import com.cissbank.basiccissbankapi.repository.LedgerTransactionRepository;
import com.cissbank.basiccissbankapi.vo.TransactionAccountLedgers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class TransactionManagerComponent {

    private final LedgerTransactionRepository ledgerTransactionRepository;
    private final LedgerRepository ledgerRepository;

    private static final Logger log = LoggerFactory.getLogger("TransactionManagerComponent");

    public TransactionManagerComponent(LedgerTransactionRepository ledgerTransactionRepository, LedgerRepository ledgerRepository) {
        this.ledgerTransactionRepository = ledgerTransactionRepository;
        this.ledgerRepository = ledgerRepository;
    }

    /**
     * Executes a transaction.
     * Rolls back to previous state if error in any step.
     * @return LedgerTransaction's id if the transfer was successful, otherwise zero.
     */
    public long executeLedgerTransaction(BigDecimal amount, int fromAccountNumber, int toAccountNumber) {

        TransactionAccountLedgers accountLedgers = validateAccountLedgers(fromAccountNumber, toAccountNumber);
        if (accountLedgers == null) {return 0;}

        ElectronicTransfer executedTransfer = writeTransactionToDatabase(amount, fromAccountNumber, toAccountNumber);
        if (executedTransfer == null) {return 0;}

        return updateBalancesAndFinishTransaction(fromAccountNumber, toAccountNumber,
                executedTransfer.getId(), executedTransfer, accountLedgers);
    }

    private ElectronicTransfer writeTransactionToDatabase(BigDecimal amount, int fromAccountNumber, int toAccountNumber) {

        ElectronicTransfer executedTransfer = null;
        ElectronicTransfer electronicTransfer = new ElectronicTransfer(amount, fromAccountNumber, toAccountNumber);

        try {
            executedTransfer = ledgerTransactionRepository.save(electronicTransfer);

        } catch (Exception exception) {
            log.error("Transaction error, nothing was written in database. [fromAccountNumber: {}] [toAccountNumber: {}]",
                    fromAccountNumber, toAccountNumber);
            return executedTransfer;
        }
        long executedTransferId = executedTransfer.getId();
        log.info("Transaction wrote in database. [fromAccountNumber: {}] [toAccountNumber: {}] [transactionId: {}]",
                fromAccountNumber, toAccountNumber, executedTransferId);
        return executedTransfer;
    }

    private TransactionAccountLedgers validateAccountLedgers(int fromAccountNumber, int toAccountNumber) {

        AccountLedger fromAccountLedger;
        AccountLedger toAccountLedger;
        AccountLedger fromAccountLedgerPreviousState;
        AccountLedger toAccountLedgerPreviousState;

        try {
            fromAccountLedger = getAccountLedgerEligibleForTransfer(fromAccountNumber);
            toAccountLedger = getAccountLedgerEligibleForTransfer(toAccountNumber);
            fromAccountLedgerPreviousState = fromAccountLedger.clone();
            toAccountLedgerPreviousState = fromAccountLedger.clone();

        } catch (Exception exception) {
            log.error("AccountLedgers eligibilities not fit for transaction. " +
                    "[fromAccountNumber: {}] [toAccountNumber: {}]", fromAccountNumber, toAccountNumber);
            return null;
        }
        log.info("AccountLedgers eligibilities validated for transaction. " +
                        "[fromAccountNumber: {}] [toAccountNumber: {}]", fromAccountNumber, toAccountNumber);

        return new TransactionAccountLedgers(fromAccountLedger, toAccountLedger, fromAccountLedgerPreviousState, toAccountLedgerPreviousState);
    }

    private long updateBalancesAndFinishTransaction(int fromAccountNumber, int toAccountNumber, long executedTransferId, ElectronicTransfer executedTransfer, TransactionAccountLedgers transactionAccountLedgers) {

        BigDecimal executedTransferAmount = executedTransfer.getAmount();

        try {
            // TODO: decide what to do with transactions which let user with negative balance;
            // I will allow for negative account balances, since there is no deposit feature yet.
            updateFromAccountBalance(transactionAccountLedgers.getFromAccountLedger(), executedTransferAmount, executedTransferId);
            updateToAccountBalance(transactionAccountLedgers.getToAccountLedger(), executedTransferAmount, executedTransferId);

        } catch (Exception exception) {
            rollbackTransaction(transactionAccountLedgers.getFromAccountLedgerPreviousState(),
                    transactionAccountLedgers.getToAccountLedgerPreviousState(), executedTransferId);
            log.error("Error while updating accounts' balances. Transaction rolled back." +
                            " [fromAccountNumber: {}] [toAccountNumber: {}] [transactionId: {}]",
                    fromAccountNumber, toAccountNumber, executedTransferId);
            return 0;
        }

        log.info("Transaction successfully updated at accounts' balances." +
                        " [fromAccountNumber: {}] [toAccountNumber: {}] [transactionId: {}]",
                fromAccountNumber, toAccountNumber, executedTransferId);

        return executedTransferId;
    }

    private void updateToAccountBalance(AccountLedger toAccountLedger, BigDecimal executedTransferAmount, long executedTransferId) {

        BigDecimal toBalance = toAccountLedger.getBalance();
        toAccountLedger.setBalance(toBalance.add(executedTransferAmount));
        toAccountLedger.setLastTransactionId(executedTransferId);
        ledgerRepository.update(toAccountLedger);
    }

    private void updateFromAccountBalance(AccountLedger fromAccountLedger, BigDecimal executedTransferAmount, long executedTransferId) {

        BigDecimal fromBalance = fromAccountLedger.getBalance();
        fromAccountLedger.setBalance(fromBalance.subtract(executedTransferAmount));
        fromAccountLedger.setLastTransactionId(executedTransferId);
        ledgerRepository.update(fromAccountLedger);
    }

    private void rollbackTransaction(AccountLedger fromAccountLedgerPreviousState,
                                     AccountLedger toAccountLedgerPreviousState, long executedTransferId) {

        ledgerRepository.update(toAccountLedgerPreviousState);
        ledgerRepository.update(fromAccountLedgerPreviousState);
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
