package com.cissbank.basiccissbankapi.repository;

import com.cissbank.basiccissbankapi.entity.ledger.LedgerTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface LedgerTransactionRepository extends PagingAndSortingRepository<LedgerTransaction, Long> {

    List<LedgerTransaction> findByFromAccountId(int fromAccountId, Pageable pageable);
    List<LedgerTransaction> findByToAccountId(int toAccountId, Pageable pageable);

    default Set<LedgerTransaction> getAccountStatement(int accountNumber, Pageable pageable) {

        Set<LedgerTransaction> transactionSet = new HashSet<>();
        List<LedgerTransaction> fromList = findByFromAccountId(accountNumber, pageable);
        List<LedgerTransaction> toList = findByToAccountId(accountNumber, pageable);

        transactionSet.addAll(fromList);
        transactionSet.addAll(toList);
        return transactionSet;
    }
}
