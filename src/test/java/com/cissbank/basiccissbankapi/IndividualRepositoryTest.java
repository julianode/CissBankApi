package com.cissbank.basiccissbankapi;

import com.cissbank.basiccissbankapi.common.enumeration.BeneficiaryType;
import com.cissbank.basiccissbankapi.common.util.CissUtils;
import com.cissbank.basiccissbankapi.entity.client.Individual;
import com.cissbank.basiccissbankapi.repository.IndividualRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IndividualRepositoryTest {

    String fredNationalRegistration = "12345678910";
    String fredName = "Fred Flintstone";
    String dirtyFredNationalRegistration = "123.456.789-10";
    String flintCoNationalRegistration = "12.345.678/0001-91";
    String flintCoName = "Flintstones Co";

    @Autowired
    IndividualRepository individualRepository;

    @Test
    void individualRepositoryWillFindIndividualWithCleanNationalRegistration() {

        Individual individual = new Individual(fredName, fredNationalRegistration);
        individualRepository.save(individual);
        Individual expectedIndividual = individualRepository.findByNationalRegistration(fredNationalRegistration);
        assertEquals(expectedIndividual, individual);
    }

    @Test
    void individualRepositoryWillNotFindIndividualWithDirtyNationalRegistration() {

        Individual individual = new Individual(fredName, dirtyFredNationalRegistration);
        individualRepository.save(individual);
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
        individualRepository.save(individual);

        String cleanFlintCoNationalRegistration = CissUtils.ensureNationalRegistrationFormat(flintCoNationalRegistration);
        Individual expectedIndividual = individualRepository.findByNationalRegistration(cleanFlintCoNationalRegistration);
        assertEquals(expectedIndividual, individual);
        assertEquals(BeneficiaryType.LEGAL_PERSON, expectedIndividual.getBeneficiaryType());
    }

    @Test
    void individualRepositoryWillFindUpdateIndividual() {

        Individual individual = new Individual(fredName, fredNationalRegistration);
        individualRepository.save(individual);
        Individual expectedIndividual = individualRepository.findByNationalRegistration(fredNationalRegistration);
        assertEquals(expectedIndividual, individual);

        String wrongName = "Fred WrongFakeSurname";
        individual.setName(wrongName);
        individualRepository.save(individual);
        expectedIndividual = individualRepository.findByNationalRegistration(fredNationalRegistration);
        assertEquals(wrongName, expectedIndividual.getName());
    }
}
