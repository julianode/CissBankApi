package com.cissbank.basiccissbankapi.entity;

import com.cissbank.basiccissbankapi.common.BeneficiaryType;

public class Individual {

    private String nationalRegistration;
    private BeneficiaryType beneficiaryType;

    public String getNationalRegistration() {
        return nationalRegistration;
    }

    public BeneficiaryType getBeneficiaryType() {
        return beneficiaryType;
    }
}
