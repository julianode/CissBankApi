package com.cissbank.basiccissbankapi;

import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;
import com.cissbank.basiccissbankapi.entity.ledger.AccountLedger;
import com.cissbank.basiccissbankapi.repository.LedgerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class LedgerRepositoryTest {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Test
    void ledgerRepositoryShouldWork() {

        final int ownerAccountNumber = 1234;
        AccountLedger accountLedger = new AccountLedger(ownerAccountNumber, BigDecimal.TEN,
                new Timestamp(System.currentTimeMillis()), 5678L, ActivationStatus.ACTIVE);

        ledgerRepository.save(accountLedger);
        AccountLedger expectedAccountLedger = ledgerRepository.findByOwnerAccountNumber(ownerAccountNumber);
        assertEquals(expectedAccountLedger, accountLedger);
    }
}
