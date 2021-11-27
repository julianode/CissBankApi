package com.cissbank.basiccissbankapi.entity.ledger;

import com.cissbank.basiccissbankapi.common.enumeration.TransferType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Used with AccountLedger.
 * Transaction fields for general money transfers.
 * Prefer to use specific subclasses.
 */
@Entity
public class LedgerTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    protected BigDecimal amount;
    protected Timestamp createdAt;
    protected int fromAccountNumber;
    protected int toAccountNumber;
    protected TransferType transferType;

    public LedgerTransaction() {}

    protected LedgerTransaction(BigDecimal amount, int fromAccountNumber, int toAccountNumber) {
        this.amount = amount;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(int fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public int getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(int toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LedgerTransaction)) return false;
        LedgerTransaction that = (LedgerTransaction) o;
        return id == that.id && fromAccountNumber == that.fromAccountNumber && toAccountNumber == that.toAccountNumber
                && Objects.equals(amount, that.amount) && Objects.equals(createdAt, that.createdAt)
                && transferType == that.transferType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, createdAt, fromAccountNumber, toAccountNumber, transferType);
    }

    @Override
    public String toString() {
        return "LedgerTransaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                ", fromAccountNumber=" + fromAccountNumber +
                ", toAccountNumber=" + toAccountNumber +
                ", transferType=" + transferType +
                '}';
    }
}
