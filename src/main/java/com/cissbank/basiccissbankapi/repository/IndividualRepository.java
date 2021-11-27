package com.cissbank.basiccissbankapi.repository;

import com.cissbank.basiccissbankapi.entity.client.Individual;
import org.springframework.data.repository.CrudRepository;

public interface IndividualRepository extends CrudRepository<Individual, Integer> {

    Individual findByNationalRegistration(String nationalRegistration);
}