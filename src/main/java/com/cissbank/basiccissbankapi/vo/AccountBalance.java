package com.cissbank.basiccissbankapi.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountBalance {

    private int accountNumber;
    private BigDecimal amount;
    private long requestedAtMillisUtc;

    public AccountBalance() {}

    public AccountBalance(int accountNumber, BigDecimal amount, long requestedAtMillisUtc) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.requestedAtMillisUtc = requestedAtMillisUtc;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getRequestedAtMillisUtc() {
        return requestedAtMillisUtc;
    }

    public void setRequestedAtMillisUtc(long requestedAtMillisUtc) {
        this.requestedAtMillisUtc = requestedAtMillisUtc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountBalance)) return false;
        AccountBalance that = (AccountBalance) o;
        return accountNumber == that.accountNumber && requestedAtMillisUtc == that.requestedAtMillisUtc && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, amount, requestedAtMillisUtc);
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
                "accountNumber=" + accountNumber +
                ", amount=" + amount +
                ", requestedAtMillisUtc=" + requestedAtMillisUtc +
                '}';
    }
}
