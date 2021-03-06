package com.cissbank.basiccissbankapi.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountBalance {

    private int accountNumber;
    private BigDecimal amount;
    private long localMillisRequestedAt;

    public AccountBalance() {}

    public AccountBalance(int accountNumber, BigDecimal amount, long localMillisRequestedAt) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.localMillisRequestedAt = localMillisRequestedAt;
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

    public long getLocalMillisRequestedAt() {
        return localMillisRequestedAt;
    }

    public void setLocalMillisRequestedAt(long localMillisRequestedAt) {
        this.localMillisRequestedAt = localMillisRequestedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountBalance)) return false;
        AccountBalance that = (AccountBalance) o;
        return accountNumber == that.accountNumber && localMillisRequestedAt == that.localMillisRequestedAt
                && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, amount, localMillisRequestedAt);
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
                "accountNumber=" + accountNumber +
                ", amount=" + amount +
                ", localMillisRequestedAt=" + localMillisRequestedAt +
                '}';
    }
}
