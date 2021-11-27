package com.cissbank.basiccissbankapi.service;

import com.cissbank.basiccissbankapi.entity.Account;
import com.cissbank.basiccissbankapi.entity.Individual;
import org.springframework.web.bind.annotation.*;

/**
 * There is no "update account" service, for now.
 * One must create another account and delete old ones as necessary.
 */
@RestController
public class AccountCreationService {

    @PostMapping("/account")
    public Account createAccount(@RequestParam(value="name") String name,
                                 @RequestParam(value="nationalRegistration") String nationalRegistration) {

        Individual newClient = new Individual(name, nationalRegistration);
        // if individual exists, make it INACTIVE
        // TODO: save Individual

        Account newAccount = new Account(nationalRegistration);
        // TODO: save Account

        return newAccount;
    }

    @GetMapping("/account")
    public Account getAccount(@RequestParam(value="nationalRegistration") String nationalRegistration) {
        return null; // TODO: develop how to get an account from database
        // talk about status and what is lacking and that what is lacking are a future feature.
    }

    @DeleteMapping("/account")
    public void deleteAccount(@RequestParam(value="accountNumber") String accountNumber) {
        // TODO: just set account status INACTIVE
        // if none other account is linked to this individual, then set individual as INACTIVE too
    }
}
