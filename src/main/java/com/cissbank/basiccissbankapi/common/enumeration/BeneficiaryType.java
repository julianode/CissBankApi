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

    /**
     * @throws InvalidParameterException in case the beneficiary type does not match any expected case.
     */
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

    /**
     * @throws InvalidParameterException in case the national registration has not either 11 neither 14 characters length.
     */
    public static BeneficiaryType fromNationalRegistration(String nationalRegistration) {

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
