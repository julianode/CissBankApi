package com.cissbank.basiccissbankapi.repository;

import com.cissbank.basiccissbankapi.entity.client.Account;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    /**
     * Use CissUtils.ensureNationalRegistrationFormat() before inputting the ownerNationalRegistration param.
     */
    List<Account> findByOwnerNationalRegistration(String ownerNationalRegistration);

    // TODO: make this work.
    Account findByNumber(int number);

    /**
     * @throws DataIntegrityViolationException for duplicated entries.
     */
    default Account persist(Account account) {

        int number = account.getNumber();
        Account existingAccount = findByNumber(number);
        if (existingAccount != null) {
            String message = String.format("Account number is duplicated. [number: %d]", number);
            throw new DataIntegrityViolationException(message);
        }
        return save(account);
    }

    /**
     * @throws DataIntegrityViolationException for non-existing entries.
     */
    default Account update(Account account) {

        int number = account.getNumber();
        Account existingAccount = findByNumber(number);
        if (existingAccount == null) {
            String message = String.format("Account number is unique. [number: %d]", number);
            throw new DataIntegrityViolationException(message);
        }
        return save(account);
    }
}
