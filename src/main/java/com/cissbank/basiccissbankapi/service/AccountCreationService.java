package com.cissbank.basiccissbankapi.service;

import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;
import com.cissbank.basiccissbankapi.common.util.CissUtils;
import com.cissbank.basiccissbankapi.entity.client.Account;
import com.cissbank.basiccissbankapi.entity.client.Individual;
import com.cissbank.basiccissbankapi.entity.ledger.AccountLedger;
import com.cissbank.basiccissbankapi.repository.AccountRepository;
import com.cissbank.basiccissbankapi.repository.IndividualRepository;
import com.cissbank.basiccissbankapi.repository.LedgerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    
    @Autowired
    private LedgerRepository ledgerRepository;

    private static final Logger log = LoggerFactory.getLogger("AccountCreationService");

    @PostMapping("/account")
    public Account createAccount(@RequestParam(value="name") String name,
                                 @RequestParam(value="nationalRegistration") String nationalRegistration,
                                 @RequestParam(value="shouldHaveInitialDeposit") boolean shouldHaveInitialDeposit) {

        Individual newUser = new Individual(name, nationalRegistration);
        String cleanNationalRegistration = CissUtils.ensureNationalRegistrationFormat(nationalRegistration);
        Individual existingUser = individualRepository.findByNationalRegistration(cleanNationalRegistration);

        handleUserSituation(newUser, existingUser);
        Account newAccount = createNewAccount(nationalRegistration, shouldHaveInitialDeposit);
        createNewAccountLedger(newAccount);

        return newAccount;
    }

    private void createNewAccountLedger(Account newAccount) {

        AccountLedger accountLedger = new AccountLedger(newAccount.getNumber(),
                BigDecimal.ZERO,0L);
        accountLedger = ledgerRepository.persist(accountLedger);
        log.info("New ledger created to account. [accountId: {}] [ledgerId: {}]", newAccount.getId(), accountLedger.getId());
    }

    private Account createNewAccount(String nationalRegistration, boolean shouldHaveInitialDeposit) {

        Account newAccount = new Account(accountRepository.generateAccountNumber(), nationalRegistration, shouldHaveInitialDeposit);
        newAccount = accountRepository.persist(newAccount);
        log.info("New account created and bound to user. [accountId: {}] [ownerNationalRegistration: {}]",
                newAccount.getId(), newAccount.getOwnerNationalRegistration());

        return newAccount;
    }

    private void handleUserSituation(Individual newUser, Individual existingUser) {

        if (existingUser != null) {
            if (existingUser.getStatus() != ActivationStatus.ACTIVE) {
                existingUser.setStatus(ActivationStatus.ACTIVE);
                individualRepository.update(existingUser);
                log.info("Old user coming back ACTIVE. [individualId: {}]", existingUser.getId());
            }
        } else {
            newUser = individualRepository.persist(newUser);
            log.info("New user created and saved. [individualId: {}]", newUser.getId());
        }
    }

    @PostMapping("/account/approve")
    public ResponseEntity<String> approveAccount(@RequestParam(value="accountNumber") int accountNumber,
                                                 @RequestParam(value="demonstrationAccount") boolean demonstrationAccount) {

        Account account = accountRepository.findByNumber(accountNumber);
        if (account == null || account.getStatus() == ActivationStatus.DEPRECATED) {
            log.error("Account seeking for approval not found. [accountNumber: {}]", accountNumber);
            return ResponseEntity.notFound().build();
        }
        log.info("Account seeking for approval found. [accountNumber: {}]", accountNumber);

        if (account.getStatus() == ActivationStatus.ACTIVE) {
            String message = String.format("Account already approved/active. [accountNumber: %d]", accountNumber);
            log.error(message);
            return ResponseEntity.ok(message);
        }
        return executeAccountApprovalDecisionMaking(account, demonstrationAccount, accountNumber);
    }

    /**
     * @throws IllegalStateException in the case the individual is lacking regulatory information or has not met deposit condition.
     */
    private ResponseEntity<String> executeAccountApprovalDecisionMaking(Account account, boolean demonstrationAccount, int accountNumber) {

        boolean depositHappened = false;
        boolean shouldHaveInitialDeposit = account.getShouldHaveInitialDeposit();

        if (shouldHaveInitialDeposit) {
            depositHappened = account.getInitialDepositDate() != null;
        }

        if (!shouldHaveInitialDeposit && demonstrationAccount) {
            return approveDemonstrationAccount(account, accountNumber);

        } else if (shouldHaveInitialDeposit && !depositHappened) {
            String message = String.format("Deposit condition not met. [accountNumber: %d]", accountNumber);
            log.info(message);
            throw new IllegalStateException(message);

        } else if (shouldHaveInitialDeposit && depositHappened && demonstrationAccount) {
            return approveDemonstrationAccount(account, accountNumber);

        } else if (!shouldHaveInitialDeposit && !demonstrationAccount) {
            String ownerNationalRegistration = account.getOwnerNationalRegistration();
            Individual individual = individualRepository.findByNationalRegistration(ownerNationalRegistration);

            if (individual.isLackingRegulatoryInformation()) {
                String descriptionMessage = String.format("The individual is lacking regulatory information." +
                        " [nationalRegistration: %s]", ownerNationalRegistration);
                log.error(descriptionMessage);
                throw new IllegalStateException(descriptionMessage);

            } else {
                return approveRegularAccount(account, accountNumber);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity<String> approveRegularAccount(Account account, int accountNumber) {

        account.setStatus(ActivationStatus.ACTIVE);
        accountRepository.update(account);

        AccountLedger accountLedger = ledgerRepository.findByOwnerAccountNumber(accountNumber);
        accountLedger.setStatus(ActivationStatus.ACTIVE);
        ledgerRepository.update(accountLedger);

        String message = String.format("Account approved/active. [accountNumber: %d]", accountNumber);
        log.info(message);
        return ResponseEntity.ok(message);
    }

    private ResponseEntity<String> approveDemonstrationAccount(Account account, int accountNumber) {

        account.setStatus(ActivationStatus.ACTIVE);
        account.setDemonstrationAccount(true);
        accountRepository.update(account);

        AccountLedger accountLedger = ledgerRepository.findByOwnerAccountNumber(accountNumber);
        accountLedger.setStatus(ActivationStatus.ACTIVE);
        ledgerRepository.update(accountLedger);

        String message = String.format("Account approved/active. [accountNumber: %d]", accountNumber);
        log.info(message);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/account")
    public Account getAccount(@RequestParam(value="accountNumber") int accountNumber) {
        log.info("Account requested. [accountNumber: {}]", accountNumber);
        return accountRepository.findByNumber(accountNumber);
    }

    @GetMapping("/accounts")
    public List<Account> getAccounts(@RequestParam(value="nationalRegistration") String nationalRegistration) {

        log.info("Individual's all accounts requested. [nationalRegistration: {}]", nationalRegistration);
        String cleanNationalRegistration = CissUtils.ensureNationalRegistrationFormat(nationalRegistration);
        return accountRepository.findByOwnerNationalRegistration(cleanNationalRegistration);
    }

    @DeleteMapping("/account")
    public ResponseEntity<String> deleteAccount(@RequestParam(value="accountNumber") int accountNumber) {

        Account account = accountRepository.findByNumber(accountNumber);

        if (account == null || account.getStatus() == ActivationStatus.DEPRECATED) {
            log.info("Account seeking for deletion not found. [accountNumber: {}]", accountNumber);
            return ResponseEntity.notFound().build();
        }
        log.info("Account seeking for deletion found. [accountNumber: {}]", accountNumber);

        account.setStatus(ActivationStatus.DEPRECATED);
        accountRepository.update(account);
        
        AccountLedger accountLedger = ledgerRepository.findByOwnerAccountNumber(accountNumber);
        if (accountLedger != null) {
            accountLedger.setStatus(ActivationStatus.DEPRECATED);
            accountLedger = ledgerRepository.update(accountLedger);
            log.info("AccountLedger (related to Account) deleted. [accountNumber: {}] [ledgerId: {}]", accountNumber, accountLedger.getId());

        } else {
            log.error("AccountLedger (related to Account) not deleted. [accountNumber: {}] [ledgerId: {}]", accountNumber, accountLedger.getId());
        }
        String message = String.format("Account deleted. [accountNumber: %d]", accountNumber);
        return ResponseEntity.ok(message);
    }
}
