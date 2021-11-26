package com.cissbank.basiccissbankapi.entity;

import com.cissbank.basiccissbankapi.vo.BankDetails;

import java.util.Objects;

public class Account {

    private final Individual owner;
    private static final int ISPB = 9999; // fake bank identification number, only for demonstration purposes.
    private static final int BRANCH_NUMBER = 1; // this fintech has only one branch
    private final int number; // number is the id too

    public Account(Individual owner, int number) {
        this.owner = owner;
        this.number = number;
    }

    public Individual getOwner() {
        return owner;
    }

    public int getNumber() {
        return number;
    }

    public BankDetails bankDetails() {
        return new BankDetails(owner, ISPB, BRANCH_NUMBER, number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return number == account.number && Objects.equals(owner, account.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, number);
    }

    @Override
    public String toString() {
        return "Account{" +
                "owner=" + owner +
                ", number=" + number +
                '}';
    }
}
