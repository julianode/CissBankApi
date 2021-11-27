package com.cissbank.basiccissbankapi.entity.ledger;

import com.cissbank.basiccissbankapi.common.enumeration.TransferType;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class ElectronicTransfer extends LedgerTransaction {

    public ElectronicTransfer(BigDecimal amount, int fromAccountNumber, int toAccountNumber) {
        super(amount, fromAccountNumber, toAccountNumber);
        this.transferType = TransferType.TED;
    }
}
