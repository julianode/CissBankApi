package com.cissbank.basiccissbankapi.repository;

import com.cissbank.basiccissbankapi.entity.client.Account;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    static final int INITIAL_ACCOUNT_NUMBER_TEMPLATE = 1001000; // pretend that there is a validation behind this template.

    /**
     * Use CissUtils.ensureNationalRegistrationFormat() before inputting the ownerNationalRegistration param.
     */
    List<Account> findByOwnerNationalRegistration(String ownerNationalRegistration);

    Account findTopByOrderByIdDesc();

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

    /**
     * Generated by the AccountRepository in order to the generated number comply with any kind of algorithmic validation.
     * Therefore, the database may not manage it.
     */
    default int generateAccountNumber() {

        Account account = findTopByOrderByIdDesc();

        if (account != null) {
            return account.getNumber() + 1; // add format validation here.

        } else {
            return INITIAL_ACCOUNT_NUMBER_TEMPLATE; // first account ever.
        }
    }
}
