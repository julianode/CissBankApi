package com.cissbank.basiccissbankapi.service;

import com.cissbank.basiccissbankapi.entity.ledger.LedgerTransaction;
import com.cissbank.basiccissbankapi.vo.AccountBalance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountUtilitiesService {

    @GetMapping("/balance")
    public AccountBalance getAccountBalance(@RequestParam(value="accountNumber") int accountNumber) {
        // TODO: get account balance from database.
        return new AccountBalance();
    }

    @GetMapping("/statement")
    public List<LedgerTransaction> getAccountStatement(@RequestParam(value="accountNumber") int accountNumber,
                                                       @RequestParam(value="offset") int offset,
                                                       @RequestParam(value="limit") int limit) {
        // TODO: get transactions from database with pagination.
        return new ArrayList<>();
    }

    @PostMapping("/transfer")
    public LedgerTransaction transfer(@RequestParam(value="fromAccountNumber") int fromAccountNumber,
                                      @RequestParam(value="toAccountNumber") int toAccountNumber,
                                      @RequestParam(value="amount") BigDecimal amount) {
        // TODO: get AccountLedger, if ACTIVE, do a transaction. Save transaction and update ledger.
        return new LedgerTransaction();
    }
}
