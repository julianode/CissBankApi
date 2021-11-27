package com.cissbank.basiccissbankapi.entity;

import com.cissbank.basiccissbankapi.common.ActivationStatus;
import com.cissbank.basiccissbankapi.common.BeneficiaryType;
import com.cissbank.basiccissbankapi.common.CissUtils;
import com.cissbank.basiccissbankapi.vo.BankDetails;

import java.util.Objects;

public class Account {

    private int number; // number is the id too
    private final String ownerNationalRegistration;
    public static final int ISPB = 9999; // fake bank identification number, only for demonstration purposes.
    public static final int BRANCH_NUMBER = 1; // this fintech has only one branch
    private ActivationStatus status;

    public Account(String ownerNationalRegistration) {
        this.ownerNationalRegistration = CissUtils.ensureNationalRegistrationFormat(ownerNationalRegistration);
        this.status = ActivationStatus.INACTIVE;
    }

    public String getOwnerNationalRegistration() {
        return ownerNationalRegistration;
    }

    public int getNumber() {
        return number;
    }

    public ActivationStatus getStatus() {
        return status;
    }

    public void setStatus(ActivationStatus status) {
        this.status = status;
    }

    public BankDetails bankDetails() {
        return new BankDetails(ISPB, ownerNationalRegistration,
                BeneficiaryType.fromNationalRegistration(ownerNationalRegistration).value,
                String.valueOf(BRANCH_NUMBER), String.valueOf(number));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return number == account.number && Objects.equals(ownerNationalRegistration, account.ownerNationalRegistration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerNationalRegistration, number);
    }

    @Override
    public String toString() {
        return "Account{" +
                "ownerNationalRegistration='" + ownerNationalRegistration + '\'' +
                ", number=" + number +
                '}';
    }
}
