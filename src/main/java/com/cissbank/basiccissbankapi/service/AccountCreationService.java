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
                                 @RequestParam(value="nationalRegistration") String nationalRegistration,
                                 @RequestParam(value="shouldHaveInitialDeposit") boolean shouldHaveInitialDeposit) {

        Individual newClient = new Individual(name, nationalRegistration);
        // if individual exists, make it ACTIVE
        // TODO: save Individual

        Account newAccount = new Account(nationalRegistration, shouldHaveInitialDeposit);
        // TODO: save Account

        return newAccount;
    }

    @PostMapping("/account/approve")
    public void approveAccount() {
        // ask for the optional deposit date, if needed
        // turn status active.
        // ask for a "demonstrationAccount" flag in order to be approved with only simple information.
        // otherwise, do not approve unless all the required data is given.
    }

    @GetMapping("/account")
    public Account getAccount(@RequestParam(value="nationalRegistration") String nationalRegistration) {
        return null; // TODO: develop how to get an account from database
        // talk about status and what is lacking and that what is lacking are a future feature.
    }

    @DeleteMapping("/account")
    public void deleteAccount(@RequestParam(value="accountNumber") String accountNumber) {
        // TODO: just set account status DEPRECATED
        // if none other account is linked to this individual, then set individual as INACTIVE too
    }
}
