package com.cissbank.basiccissbankapi.entity.ledger;

import com.cissbank.basiccissbankapi.common.enumeration.TransferType;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Deposit extends LedgerTransaction {

    public Deposit() {
    }

    public Deposit(BigDecimal amount, int toAccountNumber) {
        super(amount, 0, toAccountNumber);
        this.transferType = TransferType.DEPOSIT;
    }
}
