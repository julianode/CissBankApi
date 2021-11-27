package com.cissbank.basiccissbankapi.entity.client;

import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;
import com.cissbank.basiccissbankapi.common.enumeration.BeneficiaryType;
import com.cissbank.basiccissbankapi.common.util.CissUtils;
import com.cissbank.basiccissbankapi.vo.BankDetails;

import javax.annotation.Generated;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * Bank Account Representation for this institution (CiSS Bank).
 */
@Entity
public class Account {
    // add UNIQUE CONSTRAINT ownerNationalRegistration-number

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int number;
    private String ownerNationalRegistration;
    public static final int ISPB = 9999; // fake bank identification number, only for demonstration purposes.
    public static final int BRANCH_NUMBER = 1; // this fintech has only one branch
    private ActivationStatus status;
    private boolean shouldHaveInitialDeposit;
    private Timestamp initialDepositDate;
    private boolean demonstrationAccount;

    public Account() {}

    public Account(String ownerNationalRegistration, boolean shouldHaveInitialDeposit) {
        this.ownerNationalRegistration = CissUtils.ensureNationalRegistrationFormat(ownerNationalRegistration);
        this.status = ActivationStatus.INACTIVE;
        this.shouldHaveInitialDeposit = shouldHaveInitialDeposit;
        this.initialDepositDate = null;
        this.demonstrationAccount = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getOwnerNationalRegistration() {
        return ownerNationalRegistration;
    }

    public void setOwnerNationalRegistration(String ownerNationalRegistration) {
        this.ownerNationalRegistration = ownerNationalRegistration;
    }

    public ActivationStatus getStatus() {
        return status;
    }

    public void setStatus(ActivationStatus status) {
        this.status = status;
    }

    public boolean shouldHaveInitialDeposit() {
        return shouldHaveInitialDeposit;
    }

    public void setShouldHaveInitialDeposit(boolean shouldHaveInitialDeposit) {
        this.shouldHaveInitialDeposit = shouldHaveInitialDeposit;
    }

    public Timestamp getInitialDepositDate() {
        return initialDepositDate;
    }

    public void setInitialDepositDate(Timestamp initialDepositDate) {
        this.initialDepositDate = initialDepositDate;
    }

    public boolean isDemonstrationAccount() {
        return demonstrationAccount;
    }

    public void setDemonstrationAccount(boolean demonstrationAccount) {
        this.demonstrationAccount = demonstrationAccount;
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
        return id == account.id && number == account.number && shouldHaveInitialDeposit == account.shouldHaveInitialDeposit
                && demonstrationAccount == account.demonstrationAccount
                && Objects.equals(ownerNationalRegistration, account.ownerNationalRegistration)
                && status == account.status && Objects.equals(initialDepositDate, account.initialDepositDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, ownerNationalRegistration, status, shouldHaveInitialDeposit,
                initialDepositDate, demonstrationAccount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number=" + number +
                ", ownerNationalRegistration='" + ownerNationalRegistration + '\'' +
                ", status=" + status +
                ", shouldHaveInitialDeposit=" + shouldHaveInitialDeposit +
                ", initialDepositDate=" + initialDepositDate +
                ", demonstrationAccount=" + demonstrationAccount +
                '}';
    }
}
