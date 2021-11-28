package com.cissbank.basiccissbankapi;

import com.cissbank.basiccissbankapi.entity.client.Account;
import com.cissbank.basiccissbankapi.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Similar functionality as IndividualRepositoryTest.
 * Some assumptions are equal; therefore, not tested.
 */
@DataJpaTest
public class AccountRepositoryTest {

    private final String fredNationalRegistration = "12345678910";

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void accountRepositoryWillSaveAccount() {

        Account account = new Account(1234, fredNationalRegistration, false);
        accountRepository.persist(account);
        List<Account> expectedAccountList = accountRepository.findByOwnerNationalRegistration(fredNationalRegistration);
        assertEquals(expectedAccountList.get(0), account);

        Account account1 = accountRepository.findTopByOrderByIdDesc();
        assertEquals(account, account1);
    }

    @Test
    void accountRepositoryWillNotFindTopPosition() {

        Account account1 = accountRepository.findByNumber(1234);
        assertNull(account1);

        Account account2 = accountRepository.findTopByOrderByIdDesc();
        assertNull(account2);
    }

    @Test
    void accountRepositoryWillGenerateAccountNumbersCorrectlyAndManyAccountsForSameIndividual() {

        int number = accountRepository.generateAccountNumber();
        assertEquals(accountRepository.INITIAL_ACCOUNT_NUMBER_TEMPLATE, number);

        Account account = new Account(accountRepository.generateAccountNumber(), fredNationalRegistration, false);
        account = accountRepository.persist(account);
        assertEquals(number, account.getNumber());

        Account account1 = new Account(accountRepository.generateAccountNumber(), fredNationalRegistration, false);
        account1 = accountRepository.persist(account1);
        assertEquals(number + 1, account1.getNumber());
    }
}
