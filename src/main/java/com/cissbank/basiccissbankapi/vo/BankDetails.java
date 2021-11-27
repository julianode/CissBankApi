package com.cissbank.basiccissbankapi.vo;

import com.cissbank.basiccissbankapi.common.enumeration.BeneficiaryType;
import com.cissbank.basiccissbankapi.common.util.CissUtils;

import java.util.Objects;

public class BankDetails {

    private int ispb;
    private String nationalRegistration;
    private String beneficiaryType;
    private String branchNumber;
    private String accountNumber;

    public BankDetails(int ispb, String nationalRegistration, String beneficiaryType, String branchNumber, String accountNumber) {
        this.ispb = ispb;
        this.nationalRegistration = CissUtils.ensureNationalRegistrationFormat(nationalRegistration);
        this.beneficiaryType = BeneficiaryType.fromString(beneficiaryType).value;
        this.branchNumber = CissUtils.ensureAccountNumberSize(branchNumber);
        this.accountNumber = CissUtils.ensureBranchNumberSize(accountNumber);
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
