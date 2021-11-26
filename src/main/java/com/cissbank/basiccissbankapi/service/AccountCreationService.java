package com.cissbank.basiccissbankapi.service;

import com.cissbank.basiccissbankapi.entity.Account;
import com.cissbank.basiccissbankapi.entity.Individual;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountCreationService {

    @PostMapping("/account")
    public Account createAccount() {

        Individual newClient = new Individual(); // TODO: enhance
        int accountNumber = generateNewAccountNumber();

        Account newAccount = new Account(newClient, accountNumber);
        // TODO: save account in the database

        return newAccount;
    }

    /**
     * Checks the last account number created in the database,
     * increments one and returns it.
     */
    private int generateNewAccountNumber() {

        int lastAccountNumberCreated = 0; // TODO: checks the last account number created
        return ++lastAccountNumberCreated; // increments it and returns
    }
}
