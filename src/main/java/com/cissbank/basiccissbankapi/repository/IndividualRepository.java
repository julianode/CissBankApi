package com.cissbank.basiccissbankapi.repository;

import com.cissbank.basiccissbankapi.entity.client.Individual;
import org.springframework.data.repository.CrudRepository;

public interface IndividualRepository extends CrudRepository<Individual, Integer> {

    /**
     * Use CissUtils.ensureNationalRegistrationFormat() before inputting the nationalRegistration param.
     */
    Individual findByNationalRegistration(String nationalRegistration);
}