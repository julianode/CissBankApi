package com.cissbank.basiccissbankapi.entity.ledger;

import com.cissbank.basiccissbankapi.common.enumeration.TransferType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Used with AccountLedger.
 * Transaction fields for general money transfers.
 * Prefer to use specific subclasses.
 */
public class LedgerTransaction {

    protected long id;
    protected BigDecimal amount;
    protected Timestamp createdAt;
    protected int fromAccountId;
    protected int toAccountId;
    protected TransferType transferType;

    public LedgerTransaction() {}

    protected LedgerTransaction(BigDecimal amount, int fromAccountId, int toAccountId) {
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
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

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
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
        return id == that.id && fromAccountId == that.fromAccountId && toAccountId == that.toAccountId
                && Objects.equals(amount, that.amount) && Objects.equals(createdAt, that.createdAt)
                && transferType == that.transferType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, createdAt, fromAccountId, toAccountId, transferType);
    }

    @Override
    public String toString() {
        return this.getClass() + "{" +
                "id=" + id +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", transferType=" + transferType +
                '}';
    }
}
