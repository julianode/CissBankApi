package com.cissbank.basiccissbankapi.repository;

import com.cissbank.basiccissbankapi.entity.client.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    /**
     * Use CissUtils.ensureNationalRegistrationFormat() before inputting the ownerNationalRegistration param.
     */
    List<Account> findByOwnerNationalRegistration(String ownerNationalRegistration);

    // TODO: make this work.
    Account findByNumber(int number);
}
