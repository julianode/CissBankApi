package com.cissbank.basiccissbankapi.entity.ledger;

import com.cissbank.basiccissbankapi.common.enumeration.TransferType;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Withdrawal extends LedgerTransaction {

    public Withdrawal(BigDecimal amount, int fromAccountNumber) {
        super(amount, fromAccountNumber, 0);
        this.transferType = TransferType.WITHDRAWAL;
    }
}
