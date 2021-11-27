package com.cissbank.basiccissbankapi;

import com.cissbank.basiccissbankapi.entity.client.Account;
import com.cissbank.basiccissbankapi.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Similar functionality as IndividualRepositoryTest.
 * Some assumptions are equal; therefore, not tested.
 */
@DataJpaTest
public class AccountRepositoryTest {

    String fredNationalRegistration = "12345678910";

    @Autowired
    AccountRepository accountRepository;

    @Test
    void accountRepositoryWillSaveAccount() {

        Account account = new Account(fredNationalRegistration, false);
        accountRepository.save(account);
        List<Account> expectedAccountList = accountRepository.findByOwnerNationalRegistration(fredNationalRegistration);
        assertEquals(expectedAccountList.get(0), account);
    }

    // TODO: test number generation
    // TODO: test initialDepositDate saving
}
