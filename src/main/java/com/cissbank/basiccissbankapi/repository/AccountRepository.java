package com.cissbank.basiccissbankapi.repository;

import com.cissbank.basiccissbankapi.entity.client.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    /**
     * Use CissUtils.ensureNationalRegistrationFormat() before inputting the ownerNationalRegistration param.
     */
    Account findByOwnerNationalRegistration(String ownerNationalRegistration);
}
