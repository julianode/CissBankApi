package com.cissbank.basiccissbankapi;

import com.cissbank.basiccissbankapi.entity.client.Individual;
import com.cissbank.basiccissbankapi.repository.IndividualRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class IndividualRepositoryTest {

    String fredNationalRegistration = "12345678910";
    String fredName = "Fred Flintstone";

    @Autowired
    IndividualRepository individualRepository;

    @Test
    void IndividualRepositoryShouldWork() {

        Individual individual1 = new Individual(fredName, fredNationalRegistration);
        individualRepository.save(individual1);

        Individual expectedIndividual1 = individualRepository.findByNationalRegistration(fredNationalRegistration);
        assertEquals(expectedIndividual1, individual1);
    }
}
