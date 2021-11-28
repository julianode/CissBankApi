package com.cissbank.basiccissbankapi.vo;

import com.cissbank.basiccissbankapi.entity.ledger.AccountLedger;

import java.util.Objects;

public class TransactionAccountLedgers {

    private final AccountLedger fromAccountLedger;
    private final AccountLedger toAccountLedger;
    private final AccountLedger fromAccountLedgerPreviousState;
    private final AccountLedger toAccountLedgerPreviousState;

    public TransactionAccountLedgers(AccountLedger fromAccountLedger, AccountLedger toAccountLedger,
                                     AccountLedger fromAccountLedgerPreviousState, AccountLedger toAccountLedgerPreviousState) {
        this.fromAccountLedger = fromAccountLedger;
        this.toAccountLedger = toAccountLedger;
        this.fromAccountLedgerPreviousState = fromAccountLedgerPreviousState;
        this.toAccountLedgerPreviousState = toAccountLedgerPreviousState;
    }

    public AccountLedger getFromAccountLedger() {
        return fromAccountLedger;
    }

    public AccountLedger getToAccountLedger() {
        return toAccountLedger;
    }

    public AccountLedger getFromAccountLedgerPreviousState() {
        return fromAccountLedgerPreviousState;
    }

    public AccountLedger getToAccountLedgerPreviousState() {
        return toAccountLedgerPreviousState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionAccountLedgers)) return false;
        TransactionAccountLedgers that = (TransactionAccountLedgers) o;
        return Objects.equals(fromAccountLedger, that.fromAccountLedger) && Objects.equals(toAccountLedger, that.toAccountLedger) && Objects.equals(fromAccountLedgerPreviousState, that.fromAccountLedgerPreviousState) && Objects.equals(toAccountLedgerPreviousState, that.toAccountLedgerPreviousState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromAccountLedger, toAccountLedger, fromAccountLedgerPreviousState, toAccountLedgerPreviousState);
    }

    @Override
    public String toString() {
        return "TransactionAccountLedgers{" +
                "fromAccountLedger=" + fromAccountLedger +
                ", toAccountLedger=" + toAccountLedger +
                ", fromAccountLedgerPreviousState=" + fromAccountLedgerPreviousState +
                ", toAccountLedgerPreviousState=" + toAccountLedgerPreviousState +
                '}';
    }
}
