package com.cissbank.basiccissbankapi.entity;

import com.cissbank.basiccissbankapi.common.IdentificationDocumentType;

import java.sql.Timestamp;
import java.util.Objects;

public class IdentificationDocument {

    private final String number; // number is the id too
    private final IdentificationDocumentType identificationDocType;
    private final String expeditionAgency;
    private final Timestamp expeditionDate;
    private final String ownerNationalRegistration;

    public IdentificationDocument(String number, IdentificationDocumentType identificationDocType, String expeditionAgency,
                                  Timestamp expeditionDate, String ownerNationalRegistration) {
        this.number = number;
        this.identificationDocType = identificationDocType;
        this.expeditionAgency = expeditionAgency;
        this.expeditionDate = expeditionDate;
        this.ownerNationalRegistration = ownerNationalRegistration;
    }

    public String getNumber() {
        return number;
    }

    public IdentificationDocumentType getIdentificationDocType() {
        return identificationDocType;
    }

    public String getExpeditionAgency() {
        return expeditionAgency;
    }

    public Timestamp getExpeditionDate() {
        return expeditionDate;
    }

    public String getOwnerNationalRegistration() {
        return ownerNationalRegistration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentificationDocument)) return false;
        IdentificationDocument that = (IdentificationDocument) o;
        return Objects.equals(number, that.number) && identificationDocType == that.identificationDocType
                && Objects.equals(expeditionAgency, that.expeditionAgency) && Objects.equals(expeditionDate, that.expeditionDate)
                && Objects.equals(ownerNationalRegistration, that.ownerNationalRegistration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, identificationDocType, expeditionAgency, expeditionDate, ownerNationalRegistration);
    }

    @Override
    public String toString() {
        return "IdentificationDocument{" +
                "number='" + number + '\'' +
                ", identificationDocType=" + identificationDocType +
                ", expeditionAgency='" + expeditionAgency + '\'' +
                ", expeditionDate=" + expeditionDate +
                ", ownerNationalRegistration='" + ownerNationalRegistration + '\'' +
                '}';
    }
}
