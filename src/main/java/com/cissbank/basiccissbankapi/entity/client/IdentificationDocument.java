package com.cissbank.basiccissbankapi.entity.client;

import com.cissbank.basiccissbankapi.common.enumeration.IdentificationDocumentType;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Mandatory information.
 *  (pt-BR) BANCO CENTRAL DO BRASIL, Resolução n°2.025, or
 *  (en) Central Bank of Brazil, Resolution nº2,025.
 */
public class IdentificationDocument {

    private int id;
    private String number;
    private IdentificationDocumentType identificationDocType;
    private String expeditionAgency;
    private Timestamp expeditionDate;
    private String ownerNationalRegistration;
    // private PhotoId identificationPhotoDocument; TODO: implement person photo id image

    public IdentificationDocument() {}

    public IdentificationDocument(String number, IdentificationDocumentType identificationDocType, String expeditionAgency,
                                  Timestamp expeditionDate, String ownerNationalRegistration) {
        this.number = number;
        this.identificationDocType = identificationDocType;
        this.expeditionAgency = expeditionAgency;
        this.expeditionDate = expeditionDate;
        this.ownerNationalRegistration = ownerNationalRegistration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public IdentificationDocumentType getIdentificationDocType() {
        return identificationDocType;
    }

    public void setIdentificationDocType(IdentificationDocumentType identificationDocType) {
        this.identificationDocType = identificationDocType;
    }

    public String getExpeditionAgency() {
        return expeditionAgency;
    }

    public void setExpeditionAgency(String expeditionAgency) {
        this.expeditionAgency = expeditionAgency;
    }

    public Timestamp getExpeditionDate() {
        return expeditionDate;
    }

    public void setExpeditionDate(Timestamp expeditionDate) {
        this.expeditionDate = expeditionDate;
    }

    public String getOwnerNationalRegistration() {
        return ownerNationalRegistration;
    }

    public void setOwnerNationalRegistration(String ownerNationalRegistration) {
        this.ownerNationalRegistration = ownerNationalRegistration;
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
                "id=" + id +
                ", number='" + number + '\'' +
                ", identificationDocType=" + identificationDocType +
                ", expeditionAgency='" + expeditionAgency + '\'' +
                ", expeditionDate=" + expeditionDate +
                ", ownerNationalRegistration='" + ownerNationalRegistration + '\'' +
                '}';
    }
}
