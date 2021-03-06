package com.cissbank.basiccissbankapi.entity.ledger;

import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Used with LedgerTransactions.
 * Holds account main information and its ability to transfer money.
 */
@Entity
public class AccountLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private int ownerAccountNumber;

    private BigDecimal balance;
    private long lastTransactionId;
    private ActivationStatus status;

    public AccountLedger() {}

    public AccountLedger(int ownerAccountNumber, BigDecimal balance,
                         long lastTransactionId) {
        this.ownerAccountNumber = ownerAccountNumber;
        this.balance = balance;
        this.lastTransactionId = lastTransactionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerAccountNumber() {
        return ownerAccountNumber;
    }

    public void setOwnerAccountNumber(int ownerAccountNumber) {
        this.ownerAccountNumber = ownerAccountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public long getLastTransactionId() {
        return lastTransactionId;
    }

    public void setLastTransactionId(long lastTransactionId) {
        this.lastTransactionId = lastTransactionId;
    }

    public ActivationStatus getStatus() {
        return status;
    }

    public void setStatus(ActivationStatus status) {
        this.status = status;
    }

    @Override
    public AccountLedger clone() {
        AccountLedger accountLedger = new AccountLedger(this.ownerAccountNumber, this.balance, this.lastTransactionId);
        accountLedger.setId(this.id);
        return accountLedger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountLedger)) return false;
        AccountLedger that = (AccountLedger) o;
        return id == that.id && ownerAccountNumber == that.ownerAccountNumber && lastTransactionId == that.lastTransactionId
                && Objects.equals(balance, that.balance) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerAccountNumber, balance, lastTransactionId, status);
    }

    @Override
    public String toString() {
        return "AccountLedger{" +
                "id=" + id +
                ", ownerAccountNumber=" + ownerAccountNumber +
                ", balance=" + balance +
                ", lastTransactionId=" + lastTransactionId +
                ", status=" + status +
                '}';
    }
}
