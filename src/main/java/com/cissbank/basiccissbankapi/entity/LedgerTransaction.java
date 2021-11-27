package com.cissbank.basiccissbankapi.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Used with AccountLedger.
 * Transaction fields for "peer to peer" money transfers.
 * Only for accounts of this bank (internal).
 */
public class LedgerTransaction {

    private long id;
    private BigDecimal amount;
    private Timestamp createdAt;
    private int fromAccountId;
    private int toAccountId;
}
