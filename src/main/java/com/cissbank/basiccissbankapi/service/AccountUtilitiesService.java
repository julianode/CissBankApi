package com.cissbank.basiccissbankapi.service;

import com.cissbank.basiccissbankapi.common.pagination.OffsetPagination;
import com.cissbank.basiccissbankapi.common.pagination.Pagination;
import com.cissbank.basiccissbankapi.entity.ledger.AccountLedger;
import com.cissbank.basiccissbankapi.entity.ledger.LedgerTransaction;
import com.cissbank.basiccissbankapi.repository.LedgerRepository;
import com.cissbank.basiccissbankapi.repository.LedgerTransactionRepository;
import com.cissbank.basiccissbankapi.service.component.TransactionManagerComponent;
import com.cissbank.basiccissbankapi.vo.AccountBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Set;

@RestController
public class AccountUtilitiesService {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private LedgerTransactionRepository ledgerTransactionRepository;

    private static final Logger log = LoggerFactory.getLogger("AccountUtilitiesService");

    @GetMapping("/balance")
    public AccountBalance getAccountBalance(@RequestParam(value="accountNumber") int accountNumber) {

        log.info("Account balance requested. [accountNumber: {}]", accountNumber);
        AccountLedger accountLedger = ledgerRepository.findByOwnerAccountNumber(accountNumber);
        return new AccountBalance(accountLedger.getOwnerAccountNumber(), accountLedger.getBalance(), System.currentTimeMillis());
    }

    @GetMapping("/statement")
    public Set<LedgerTransaction> getAccountStatement(@RequestParam(value="accountNumber") int accountNumber,
                                                      @RequestParam(value="offset") int offset,
                                                      @RequestParam(value="limit") int limit) {

        log.info("Account statement requested. [accountNumber: {}]", accountNumber);
        Pagination pagination = new OffsetPagination(limit, offset);
        Pageable pageable = PageRequest.of(pagination.getCurrentPage(), limit);
        return ledgerTransactionRepository.getAccountStatement(accountNumber, pageable);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam(value="fromAccountNumber") int fromAccountNumber,
                                   @RequestParam(value="toAccountNumber") int toAccountNumber,
                                   @RequestParam(value="amount") BigDecimal amount) {

        TransactionManagerComponent transactionManagerComponent =
                new TransactionManagerComponent(ledgerTransactionRepository, ledgerRepository);

        long transactionId = transactionManagerComponent.executeLedgerTransaction(amount, fromAccountNumber, toAccountNumber);
        boolean success = transactionId != 0;

        if (success) {
            LedgerTransaction resultTransaction = ledgerTransactionRepository.findById(transactionId).get();
            return ResponseEntity.ok(resultTransaction.toString());

        } else {
            String messageBody = String.format("\"message\":Could not process transaction. " +
                    "[fromAccountNumber: %d] [toAccountNumber: %d] [amount: %f]", fromAccountNumber, toAccountNumber, amount);
            log.error(messageBody);
            return ResponseEntity.internalServerError().body(messageBody);
        }
    }
}
