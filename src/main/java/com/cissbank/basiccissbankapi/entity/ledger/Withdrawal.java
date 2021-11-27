package com.cissbank.basiccissbankapi.entity.ledger;

import com.cissbank.basiccissbankapi.common.enumeration.TransferType;

import java.math.BigDecimal;

public class Withdrawal extends LedgerTransaction {

    public Withdrawal(BigDecimal amount, int fromAccountId) {
        super(amount, fromAccountId, 0);
        this.transferType = TransferType.WITHDRAWAL;
    }
}
