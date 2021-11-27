package com.cissbank.basiccissbankapi.entity.ledger;

import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
    private int ownerAccountNumber;
    private BigDecimal balance;
    private Timestamp lastUpdatedAt;
    private long lastTransactionId;
    private ActivationStatus status;

    public AccountLedger() {}

    public AccountLedger(int ownerAccountNumber, BigDecimal balance, Timestamp lastUpdatedAt,
                         long lastTransactionId, ActivationStatus status) {
        this.ownerAccountNumber = ownerAccountNumber;
        this.balance = balance;
        this.lastUpdatedAt = lastUpdatedAt;
        this.lastTransactionId = lastTransactionId;
        this.status = status;
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

    public Timestamp getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
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

    public AccountLedger clone() throws CloneNotSupportedException {
        return (AccountLedger) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountLedger)) return false;
        AccountLedger that = (AccountLedger) o;
        return id == that.id && ownerAccountNumber == that.ownerAccountNumber && lastTransactionId == that.lastTransactionId
                && Objects.equals(balance, that.balance) && Objects.equals(lastUpdatedAt, that.lastUpdatedAt)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerAccountNumber, balance, lastUpdatedAt, lastTransactionId, status);
    }

    @Override
    public String toString() {
        return "AccountLedger{" +
                "id=" + id +
                ", ownerAccountNumber=" + ownerAccountNumber +
                ", balance=" + balance +
                ", lastUpdatedAt=" + lastUpdatedAt +
                ", lastTransactionId=" + lastTransactionId +
                ", status=" + status +
                '}';
    }
}
