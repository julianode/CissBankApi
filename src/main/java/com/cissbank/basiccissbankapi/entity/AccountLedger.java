package com.cissbank.basiccissbankapi.entity;

import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Controls LedgerTransactions.
 * Holds account main information regarding its ability to transfer money.
 * Not ACTIVE ledgers will not let any LedgerTransaction happen.
 */
public class AccountLedger {

    private BigDecimal balance;
    private Timestamp lastUpdatedAt;
    private long lastTransactionId;
    private ActivationStatus status;
}
