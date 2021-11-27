package com.cissbank.basiccissbankapi.entity.ledger;

import com.cissbank.basiccissbankapi.common.enumeration.TransferType;

import java.math.BigDecimal;

public class Deposit extends LedgerTransaction {

    public Deposit(BigDecimal amount, int toAccountId) {
        super(amount, 0, toAccountId);
        this.transferType = TransferType.DEPOSIT;
    }
}
