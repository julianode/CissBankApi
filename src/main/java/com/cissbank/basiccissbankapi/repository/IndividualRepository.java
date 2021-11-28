package com.cissbank.basiccissbankapi.repository;

import com.cissbank.basiccissbankapi.entity.client.Individual;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;

public interface IndividualRepository extends CrudRepository<Individual, Integer> {

    /**
     * Use CissUtils.ensureNationalRegistrationFormat() before inputting the nationalRegistration param.
     */
    Individual findByNationalRegistration(String nationalRegistration);

    /**
     * @throws DataIntegrityViolationException for duplicated entries.
     */
    default Individual persist(Individual individual) {

        String nationalRegistration = individual.getNationalRegistration();
        Individual existingIndividual = findByNationalRegistration(nationalRegistration);
        if (existingIndividual != null) {
            String message = String.format("Individual nationalRegistration is duplicated. " +
                    "[nationalRegistration: %s]",nationalRegistration);
            throw new DataIntegrityViolationException(message);
        }
        return save(individual);
    }

    /**
     * @throws DataIntegrityViolationException for non-existing entries.
     */
    default Individual update(Individual individual) {

        String nationalRegistration = individual.getNationalRegistration();
        Individual existingIndividual = findByNationalRegistration(nationalRegistration);
        if (existingIndividual == null) {
            String message = String.format("Individual nationalRegistration is unique. " +
                    "[nationalRegistration: %s]",nationalRegistration);
            throw new DataIntegrityViolationException(message);
        }
        return save(individual);
    }

}