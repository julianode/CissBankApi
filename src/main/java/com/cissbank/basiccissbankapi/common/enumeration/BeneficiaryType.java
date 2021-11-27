package com.cissbank.basiccissbankapi.common.enumeration;

import com.cissbank.basiccissbankapi.common.util.CissUtils;

import java.security.InvalidParameterException;

public enum BeneficiaryType {

    PHYSICAL_PERSON ("F"), // pessoa física, F, in Brazil
    LEGAL_PERSON ("J"); // pessoa jurídica, J, in Brazil

    public final String value;

    BeneficiaryType(String value) {
        this.value = value;
    }

    public static BeneficiaryType fromString(String dirtyBeneficiaryType) throws InvalidParameterException {

        BeneficiaryType beneficiaryType;

        switch (dirtyBeneficiaryType) {

            case "f":
            case "F":
            case "fisica":
            case "physical":
            case "pessoaFisica":
            case "physicalPerson":
                beneficiaryType = PHYSICAL_PERSON;
                break;

            case "j":
            case "J":
            case "juridica":
            case "legal":
            case "pessoaJuridica":
            case "legalPerson":
                beneficiaryType = LEGAL_PERSON;
                break;

            default:
                throw new InvalidParameterException(dirtyBeneficiaryType + " is not a valid BeneficiaryType.");
        }
        return beneficiaryType;
    }

    public static BeneficiaryType fromNationalRegistration(String nationalRegistration) throws InvalidParameterException {

        nationalRegistration = CissUtils.ensureNationalRegistrationFormat(nationalRegistration);
        BeneficiaryType beneficiaryType;

        switch (nationalRegistration.length()) {

            case 11: // CPF length
                beneficiaryType = PHYSICAL_PERSON;
                break;

            case 14: // CNPJ length
                beneficiaryType = LEGAL_PERSON;
                break;

            default:
                throw new InvalidParameterException("Cannot infer a BeneficiaryType from " + nationalRegistration);
        }
        return beneficiaryType;
    }
}
