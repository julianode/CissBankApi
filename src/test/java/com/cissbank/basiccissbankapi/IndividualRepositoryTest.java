package com.cissbank.basiccissbankapi;

import com.cissbank.basiccissbankapi.common.enumeration.BeneficiaryType;
import com.cissbank.basiccissbankapi.common.util.CissUtils;
import com.cissbank.basiccissbankapi.entity.client.Individual;
import com.cissbank.basiccissbankapi.repository.IndividualRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IndividualRepositoryTest {

    private final String fredNationalRegistration = "12345678910";
    private final String fredName = "Fred Flintstone";
    private final String dirtyFredNationalRegistration = "123.456.789-10";
    private final String flintCoNationalRegistration = "12.345.678/0001-91";
    private final String flintCoName = "Flintstones Co";
    private final String wrongName = "Fred WrongFakeSurname";

    @Autowired
    private IndividualRepository individualRepository;

    @Test
    void individualRepositoryWillFindIndividualWithCleanNationalRegistration() {

        Individual individual = new Individual(fredName, fredNationalRegistration);
        individualRepository.persist(individual);
        Individual expectedIndividual = individualRepository.findByNationalRegistration(fredNationalRegistration);
        assertEquals(expectedIndividual, individual);
    }

    @Test
    void individualRepositoryWillNotFindIndividualWithDirtyNationalRegistration() {

        Individual individual = new Individual(fredName, dirtyFredNationalRegistration);
        individualRepository.persist(individual);
        Individual expectedIndividual = individualRepository.findByNationalRegistration(dirtyFredNationalRegistration);
        assertNotEquals(expectedIndividual, individual);
        assertNull(expectedIndividual);

        String cleanFredNationalRegistration = CissUtils.ensureNationalRegistrationFormat(dirtyFredNationalRegistration);
        expectedIndividual = individualRepository.findByNationalRegistration(cleanFredNationalRegistration);
        assertEquals(expectedIndividual, individual);
    }

    @Test
    void individualRepositoryWillSaveMistakenLegalPersonInfo() {

        Individual individual = new Individual(flintCoName, flintCoNationalRegistration);
        individualRepository.persist(individual);

        String cleanFlintCoNationalRegistration = CissUtils.ensureNationalRegistrationFormat(flintCoNationalRegistration);
        Individual expectedIndividual = individualRepository.findByNationalRegistration(cleanFlintCoNationalRegistration);
        assertEquals(expectedIndividual, individual);
        assertEquals(BeneficiaryType.LEGAL_PERSON, expectedIndividual.getBeneficiaryType());
    }

    @Test
    void individualRepositoryWillUpdateIndividual() {

        Individual individual = new Individual(fredName, fredNationalRegistration);
        individualRepository.persist(individual);
        Individual expectedIndividual = individualRepository.findByNationalRegistration(fredNationalRegistration);
        assertEquals(expectedIndividual, individual);

        individual.setName(wrongName);
        individualRepository.update(individual);
        expectedIndividual = individualRepository.findByNationalRegistration(fredNationalRegistration);
        assertEquals(wrongName, expectedIndividual.getName());
    }

    @Test
    void individualRepositoryWillNotDuplicateIndividual() {

        Individual individual = new Individual(fredName, fredNationalRegistration);
        individualRepository.persist(individual);
        Individual expectedIndividual = individualRepository.findByNationalRegistration(fredNationalRegistration);
        assertEquals(expectedIndividual, individual);

        Individual individual2 = new Individual(wrongName, fredNationalRegistration);
        try {
            individualRepository.persist(individual2);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            assertNotNull(dataIntegrityViolationException);
        }
    }
}
