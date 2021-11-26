package com.cissbank.basiccissbankapi.vo;

import com.cissbank.basiccissbankapi.common.BeneficiaryType;
import com.cissbank.basiccissbankapi.entity.Individual;

import java.util.Objects;

public class BankDetails {

    private int ispb;
    private String nationalRegistration;
    private String beneficiaryType;
    private String branchNumber;
    private String accountNumber;

    public BankDetails(int ispb, String nationalRegistration, String beneficiaryType, String branchNumber, String accountNumber) {
        this.ispb = ispb;
        this.nationalRegistration = nationalRegistration;
        this.beneficiaryType = BeneficiaryType.fromString(beneficiaryType).value;
        this.branchNumber = branchNumber;
        this.accountNumber = ensureBranchNumberSize(accountNumber);
    }

    public BankDetails(Individual owner, int ispb, int branchNumber, int accountNumber) {
        this.ispb = ispb;
        this.nationalRegistration = owner.getNationalRegistration();
        this.beneficiaryType = owner.getBeneficiaryType().value;
        this.branchNumber = String.valueOf(branchNumber);
        this.accountNumber = ensureBranchNumberSize(String.valueOf(accountNumber));
    }

    public int getIspb() {
        return ispb;
    }

    public void setIspb(int ispb) {
        this.ispb = ispb;
    }

    public String getNationalRegistration() {
        return nationalRegistration;
    }

    public void setNationalRegistration(String nationalRegistration) {
        this.nationalRegistration = nationalRegistration;
    }

    public BeneficiaryType getBeneficiaryType() {
        return BeneficiaryType.fromString(beneficiaryType);
    }

    public void setBeneficiaryType(BeneficiaryType beneficiaryType) {
        this.beneficiaryType = beneficiaryType.value;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Branch numbers must be 4 digits long at least.
     * Ex.: 0001, 0123, 1000, 1234, 123456789, 999999999.
     */
    private String ensureBranchNumberSize(String branchNumber) {

        StringBuilder branchNumberBuilder = new StringBuilder(branchNumber);
        while (branchNumberBuilder.length() < 4) {
            branchNumberBuilder.insert(0, "0");
        }

        return branchNumberBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankDetails)) return false;
        BankDetails that = (BankDetails) o;
        return ispb == that.ispb
                && Objects.equals(nationalRegistration, that.nationalRegistration)
                && Objects.equals(beneficiaryType, that.beneficiaryType)
                && Objects.equals(branchNumber, that.branchNumber)
                && Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ispb, nationalRegistration, beneficiaryType, branchNumber, accountNumber);
    }

    @Override
    public String toString() {
        return "BankDetails{" +
                "ispb=" + ispb +
                ", nationalRegistration='" + nationalRegistration + '\'' +
                ", beneficiaryType=" + beneficiaryType +
                ", branchNumber='" + branchNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
