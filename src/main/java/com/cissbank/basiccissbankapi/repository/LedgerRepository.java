package com.cissbank.basiccissbankapi.repository;

import com.cissbank.basiccissbankapi.entity.ledger.AccountLedger;
import org.springframework.data.repository.CrudRepository;

public interface LedgerRepository extends CrudRepository<AccountLedger, Integer> {

    AccountLedger findByOwnerAccountNumber(int ownerAccountNumber);
}
