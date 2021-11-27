package com.cissbank.basiccissbankapi.common;

public enum ActivationStatus {

    INACTIVE,    // initial status after creation
    ACTIVE,      // normal status after approval
    DEPRECATED;  // virtually deleted status
}
