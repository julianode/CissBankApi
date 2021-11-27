package com.cissbank.basiccissbankapi.service;

import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;
import com.cissbank.basiccissbankapi.common.util.CissUtils;
import com.cissbank.basiccissbankapi.entity.client.Account;
import com.cissbank.basiccissbankapi.entity.client.Individual;
import com.cissbank.basiccissbankapi.repository.AccountRepository;
import com.cissbank.basiccissbankapi.repository.IndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * There is no "update account" service, for now.
 * One must create another account and delete old ones as necessary.
 */
@RestController
public class AccountCreationService {

    @Autowired
    IndividualRepository individualRepository;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/account")
    public Account createAccount(@RequestParam(value="name") String name,
                                 @RequestParam(value="nationalRegistration") String nationalRegistration,
                                 @RequestParam(value="shouldHaveInitialDeposit") boolean shouldHaveInitialDeposit) {

        Individual newUser = new Individual(name, nationalRegistration);
        String cleanNationalRegistration = CissUtils.ensureNationalRegistrationFormat(nationalRegistration);
        Individual existingUser = individualRepository.findByNationalRegistration(cleanNationalRegistration);

        handleUserSituation(newUser, existingUser);

        Account newAccount = new Account(nationalRegistration, shouldHaveInitialDeposit);
        accountRepository.save(newAccount);

        return newAccount;
    }

    private void handleUserSituation(Individual newUser, Individual existingUser) {

        if (existingUser != null) {
            if (existingUser.getStatus() != ActivationStatus.ACTIVE) {
                existingUser.setStatus(ActivationStatus.ACTIVE);
                individualRepository.save(existingUser);
            }
        } else {
            individualRepository.save(newUser);
        }
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
    public void deleteAccount(@RequestParam(value="accountNumber") int accountNumber) {
        // TODO: just set account status DEPRECATED
        // if none other account is linked to this individual, then set individual as INACTIVE too
    }
}
