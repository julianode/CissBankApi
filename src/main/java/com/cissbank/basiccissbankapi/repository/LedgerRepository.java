package com.cissbank.basiccissbankapi.repository;

import com.cissbank.basiccissbankapi.entity.ledger.AccountLedger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;

public interface LedgerRepository extends CrudRepository<AccountLedger, Integer> {

    AccountLedger findByOwnerAccountNumber(int ownerAccountNumber);

    /**
     * @throws DataIntegrityViolationException for duplicated entries.
     */
    default AccountLedger persist(AccountLedger accountLedger) {

        int ownerAccountNumber = accountLedger.getOwnerAccountNumber();
        AccountLedger existingAccountLedger = findByOwnerAccountNumber(ownerAccountNumber);
        if (existingAccountLedger != null) {
            String message = String.format("AccountLedger ownerAccountNumber is duplicated. " +
                    "[ownerAccountNumber: %d]",ownerAccountNumber);
            throw new DataIntegrityViolationException(message);
        }
        return save(accountLedger);
    }

    /**
     * @throws DataIntegrityViolationException for non-existing entries.
     */
    default AccountLedger update(AccountLedger accountLedger) {

        int ownerAccountNumber = accountLedger.getOwnerAccountNumber();
        AccountLedger existingAccountLedger = findByOwnerAccountNumber(ownerAccountNumber);
        if (existingAccountLedger == null) {
            String message = String.format("AccountLedger ownerAccountNumber is unique. " +
                    "[ownerAccountNumber: %d]",ownerAccountNumber);
            throw new DataIntegrityViolationException(message);
        }
        return save(accountLedger);
    }
}
