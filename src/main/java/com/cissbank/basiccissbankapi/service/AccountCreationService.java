package com.cissbank.basiccissbankapi.service;

import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;
import com.cissbank.basiccissbankapi.common.util.CissUtils;
import com.cissbank.basiccissbankapi.entity.client.Account;
import com.cissbank.basiccissbankapi.entity.client.Individual;
import com.cissbank.basiccissbankapi.repository.AccountRepository;
import com.cissbank.basiccissbankapi.repository.IndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * There is no "update account" service, for now.
 * This is because, mainly only demonstration accounts work which just have 3 parameters to fill in.
 * Therefore, there is no sense in updating them.
 * One may create another account and delete old ones at will.
 */
@RestController
public class AccountCreationService {

    @Autowired
    private IndividualRepository individualRepository;

    @Autowired
    private AccountRepository accountRepository;

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
    public ResponseEntity<String> approveAccount(@RequestParam(value="accountNumber") int accountNumber,
                                                 @RequestParam(value="demonstrationAccount") boolean demonstrationAccount) {

        Account account = accountRepository.findByNumber(accountNumber);
        boolean depositHappened = false;
        boolean shouldHaveInitialDeposit = account.getShouldHaveInitialDeposit();

        if (account.getStatus() == ActivationStatus.ACTIVE) {
            String message = String.format("Account already approved/active. [accountNumber: %d]", accountNumber);
            return ResponseEntity.ok(message);
        }
        if (shouldHaveInitialDeposit) {
            depositHappened = account.getInitialDepositDate() != null;
        }
        return executeAccountApprovalDecisionMaking(account, shouldHaveInitialDeposit, demonstrationAccount,
                accountNumber, depositHappened);
    }

    /**
     * @throws IllegalStateException in the case the individual is lacking regulatory information or has not met deposit condition.
     */
    private ResponseEntity<String> executeAccountApprovalDecisionMaking(Account account, boolean shouldHaveInitialDeposit,
                                                                        boolean demonstrationAccount, int accountNumber,
                                                                        boolean depositHappened) {
        if (!shouldHaveInitialDeposit && demonstrationAccount) {
            account.setStatus(ActivationStatus.ACTIVE);
            account.setDemonstrationAccount(true);
            accountRepository.save(account);
            String message = String.format("Account approved/active. [accountNumber: %d]", accountNumber);
            return ResponseEntity.ok(message);

        } else if (shouldHaveInitialDeposit && !depositHappened) {
            String message = String.format("Deposit condition not met. [accountNumber: %d]", accountNumber);
            throw new IllegalStateException(message);

        } else if (shouldHaveInitialDeposit && depositHappened && demonstrationAccount) {
            account.setStatus(ActivationStatus.ACTIVE);
            account.setDemonstrationAccount(true);
            accountRepository.save(account);
            String message = String.format("Account approved/active. [accountNumber: %d]", accountNumber);
            return ResponseEntity.ok(message);

        } else if (!shouldHaveInitialDeposit && !demonstrationAccount) {
            String ownerNationalRegistration = account.getOwnerNationalRegistration();
            Individual individual = individualRepository.findByNationalRegistration(ownerNationalRegistration);

            if (individual.isLackingRegulatoryInformation()) {
                String descriptionMessage = String.format("The individual is lacking regulatory information." +
                        " [nationalRegistration: %s]", ownerNationalRegistration);
                throw new IllegalStateException(descriptionMessage);

            } else {
                account.setStatus(ActivationStatus.ACTIVE);
                accountRepository.save(account);
                String message = String.format("Account approved/active. [accountNumber: %d]", accountNumber);
                return ResponseEntity.ok(message);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/account")
    public Account getAccount(@RequestParam(value="accountNumber") int accountNumber) {
        return accountRepository.findByNumber(accountNumber);
    }

    @GetMapping("/accounts")
    public List<Account> getAccounts(@RequestParam(value="nationalRegistration") String nationalRegistration) {
        String cleanNationalRegistration = CissUtils.ensureNationalRegistrationFormat(nationalRegistration);
        return accountRepository.findByOwnerNationalRegistration(cleanNationalRegistration);
    }

    @DeleteMapping("/account")
    public ResponseEntity<String> deleteAccount(@RequestParam(value="accountNumber") int accountNumber) {

        Account account = accountRepository.findByNumber(accountNumber);

        if (account.getStatus() == ActivationStatus.DEPRECATED) {
            return ResponseEntity.notFound().build();
        }

        account.setStatus(ActivationStatus.DEPRECATED);
        accountRepository.save(account);
        String message = String.format("Account deleted. [accountNumber: %d]", accountNumber);
        return ResponseEntity.ok(message);
    }
}
