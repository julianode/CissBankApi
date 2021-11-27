package com.cissbank.basiccissbankapi.common.util;

public class CissUtils {

    /**
     * Removes slashes (/), dots (.) and hyphens (-) from brazilian national registrations, CPF or CNPJ.
     */
    public static String ensureNationalRegistrationFormat(String dirtyNationalRegistration) {

        String nationalRegistrationWithoutSlashes = dirtyNationalRegistration.replaceAll("/", "");
        String nationalRegistrationWithoutDots = nationalRegistrationWithoutSlashes.replaceAll("\\.", "");
        return nationalRegistrationWithoutDots.replaceAll("-", "");
    }

    /**
     * Branch numbers must be 4 digits long at least.
     * Ex.: 0001, 0123, 1000, 1234, 123456789, 999999999.
     */
    public static String ensureBranchNumberSize(String branchNumber) {

        StringBuilder branchNumberBuilder = new StringBuilder(branchNumber);
        while (branchNumberBuilder.length() < 4) {
            branchNumberBuilder.insert(0, "0");
        }

        return branchNumberBuilder.toString();
    }
}
