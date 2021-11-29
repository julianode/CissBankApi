package com.cissbank.basiccissbankapi.repository;

import com.cissbank.basiccissbankapi.entity.ledger.LedgerTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface LedgerTransactionRepository extends PagingAndSortingRepository<LedgerTransaction, Long> {

    List<LedgerTransaction> findByFromAccountNumber(int fromAccountNumber, Pageable pageable);

    List<LedgerTransaction> findByToAccountNumber(int toAccountNumber, Pageable pageable);

    default Set<LedgerTransaction> getAccountStatement(int accountNumber, Pageable pageable) {

        Set<LedgerTransaction> transactionSet = new HashSet<>();
        List<LedgerTransaction> fromList = findByFromAccountNumber(accountNumber, pageable);
        List<LedgerTransaction> toList = findByToAccountNumber(accountNumber, pageable);

        for(LedgerTransaction ledgerTransaction : fromList) {
            BigDecimal nonNegativeAmount = ledgerTransaction.getAmount();
            ledgerTransaction.setAmount(nonNegativeAmount.negate());
        }

        transactionSet.addAll(fromList);
        transactionSet.addAll(toList);
        return transactionSet;
    }
}
