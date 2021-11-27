package com.cissbank.basiccissbankapi.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class AccountBalance {

    private int accountNumber;
    private BigDecimal amount;
    private Timestamp requestedAt;

    public AccountBalance() {}

    public AccountBalance(int accountNumber, BigDecimal amount, Timestamp requestedAt) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.requestedAt = requestedAt;
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

    public Timestamp getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Timestamp requestedAt) {
        this.requestedAt = requestedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountBalance)) return false;
        AccountBalance that = (AccountBalance) o;
        return accountNumber == that.accountNumber && Objects.equals(amount, that.amount)
                && Objects.equals(requestedAt, that.requestedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, amount, requestedAt);
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
                "accountNumber=" + accountNumber +
                ", amount=" + amount +
                ", requestedAt=" + requestedAt +
                '}';
    }
}
