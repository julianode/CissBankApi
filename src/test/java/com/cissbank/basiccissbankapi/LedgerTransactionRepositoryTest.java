package com.cissbank.basiccissbankapi;

import com.cissbank.basiccissbankapi.entity.ledger.Deposit;
import com.cissbank.basiccissbankapi.entity.ledger.ElectronicTransfer;
import com.cissbank.basiccissbankapi.entity.ledger.LedgerTransaction;
import com.cissbank.basiccissbankapi.entity.ledger.Withdrawal;
import com.cissbank.basiccissbankapi.repository.LedgerTransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class LedgerTransactionRepositoryTest {

    private final int toAccount = 1234;
    private final int fromAccount = 5678;
    private final Pageable pageable = PageRequest.of(0, 10);

    @Autowired
    private LedgerTransactionRepository ledgerTransactionRepository;

    @Test
    void ledgerTransactionRepositoryShouldSaveDeposit() {

        Deposit deposit = new Deposit(BigDecimal.TEN, toAccount);
        ledgerTransactionRepository.save(deposit);
        List<LedgerTransaction> transactionList = ledgerTransactionRepository.findByToAccountNumber(toAccount, pageable);
        Deposit expectedDeposit = (Deposit) transactionList.get(0);
        assertEquals(expectedDeposit, deposit);
    }

    @Test
    void ledgerTransactionRepositoryShouldSaveWithdrawal() {

        Withdrawal withdrawal = new Withdrawal(BigDecimal.TEN, fromAccount);
        ledgerTransactionRepository.save(withdrawal);
        List<LedgerTransaction> transactionList  = ledgerTransactionRepository.findByFromAccountNumber(fromAccount, pageable);
        Withdrawal expectedWithdrawal = (Withdrawal) transactionList.get(0);
        assertEquals(expectedWithdrawal, withdrawal);
    }

    @Test
    void ledgerTransactionRepositoryShouldSaveElectronicTransfer() {

        ElectronicTransfer electronicTransfer = new ElectronicTransfer(BigDecimal.TEN, fromAccount, toAccount);
        ledgerTransactionRepository.save(electronicTransfer);
        List<LedgerTransaction> transactionList = ledgerTransactionRepository.findByFromAccountNumber(fromAccount, pageable);
        ElectronicTransfer expectedElectronicTransfer = (ElectronicTransfer) transactionList.get(0);
        assertEquals(expectedElectronicTransfer, electronicTransfer);
    }
}
