package com.cissbank.basiccissbankapi.common;

import java.security.InvalidParameterException;

public enum BeneficiaryType {

    PHYSICAL_PERSON ("F"), // pessoa física, F, in Brazil
    LEGAL_PERSON ("J"); // pessoa jurídica, J, in Brazil

    public final String value;

    BeneficiaryType(String value) {
        this.value = value;
    }

    public static BeneficiaryType fromString(String dirtyBeneficiaryType) {

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
}
