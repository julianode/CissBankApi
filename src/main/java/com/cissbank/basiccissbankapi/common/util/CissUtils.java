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

    /**
     * Branch numbers must be 8 digits long at least.
     * Ex.: 00000001, 00000123, 10000000, 12345678, 123456789, 999999999.
     */
    public static String ensureAccountNumberSize(String accountNumber) {

        StringBuilder accountNumberBuilder = new StringBuilder(accountNumber);
        while (accountNumberBuilder.length() < 8) {
            accountNumberBuilder.insert(0, "0");
        }
        return accountNumberBuilder.toString();
    }
}
