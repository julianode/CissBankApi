package com.cissbank.basiccissbankapi.entity.client;

import com.cissbank.basiccissbankapi.common.enumeration.BeneficiaryType;
import com.cissbank.basiccissbankapi.common.enumeration.Gender;
import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;
import com.cissbank.basiccissbankapi.common.util.CissUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents an Individual, a physical person.
 * Fields according to:
 * (pt-BR) BANCO CENTRAL DO BRASIL, Resolução n°2.025, or
 * (en) Central Bank of Brazil, Resolution nº2,025.
 * Not to be confused with a legal person.
 */
@Entity
public class Individual {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private Timestamp dateOfBirth;
    private String nationalRegistration; // CPF, or CNPJ (Brazilian documents)
    private BeneficiaryType beneficiaryType;
    private String motherName;
    private String countyOfBirth;
    private String spouseName;
    private Gender gender;
    private boolean isMarried;
    private String profession;
    private int deliveryAddressId;
    private int billingAddressId;
    private String identificationDocNumber;
    private ActivationStatus status; // Individuals may not be DEPRECATED, unless dead.

    public Individual() {}

    public Individual(String name, String nationalRegistration) {
        this.name = name;
        this.nationalRegistration = CissUtils.ensureNationalRegistrationFormat(nationalRegistration);
        this.beneficiaryType = BeneficiaryType.fromNationalRegistration(nationalRegistration);
        this.status = ActivationStatus.INACTIVE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationalRegistration() {
        return nationalRegistration;
    }

    public void setNationalRegistration(String nationalRegistration) {
        this.nationalRegistration = nationalRegistration;
    }

    public BeneficiaryType getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(BeneficiaryType beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getCountyOfBirth() {
        return countyOfBirth;
    }

    public void setCountyOfBirth(String countyOfBirth) {
        this.countyOfBirth = countyOfBirth;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(int deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public int getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(int billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public String getIdentificationDocNumber() {
        return identificationDocNumber;
    }

    public void setIdentificationDocNumber(String identificationDocNumber) {
        this.identificationDocNumber = identificationDocNumber;
    }

    public ActivationStatus getStatus() {
        return status;
    }

    public void setStatus(ActivationStatus status) {
        this.status = status;
    }

    public boolean isLackingRegulatoryInformation() {
        return dateOfBirth == null
                || motherName == null
                || countyOfBirth == null
                || spouseName == null
                || gender == null
                || profession == null
                || deliveryAddressId == 0
                || billingAddressId == 0
                || identificationDocNumber == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Individual)) return false;
        Individual that = (Individual) o;
        return id == that.id && isMarried == that.isMarried && deliveryAddressId == that.deliveryAddressId
                && billingAddressId == that.billingAddressId && Objects.equals(name, that.name)
                && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(nationalRegistration, that.nationalRegistration)
                && beneficiaryType == that.beneficiaryType && Objects.equals(motherName, that.motherName)
                && Objects.equals(countyOfBirth, that.countyOfBirth) && Objects.equals(spouseName, that.spouseName)
                && gender == that.gender && Objects.equals(profession, that.profession)
                && Objects.equals(identificationDocNumber, that.identificationDocNumber) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfBirth, nationalRegistration, beneficiaryType, motherName,
                countyOfBirth, spouseName, gender, isMarried, profession, deliveryAddressId, billingAddressId,
                identificationDocNumber, status);
    }

    @Override
    public String toString() {
        return "Individual{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", nationalRegistration='" + nationalRegistration + '\'' +
                ", beneficiaryType=" + beneficiaryType +
                ", motherName='" + motherName + '\'' +
                ", countyOfBirth='" + countyOfBirth + '\'' +
                ", spouseName='" + spouseName + '\'' +
                ", gender=" + gender +
                ", isMarried=" + isMarried +
                ", profession='" + profession + '\'' +
                ", deliveryAddressId=" + deliveryAddressId +
                ", billingAddressId=" + billingAddressId +
                ", identificationDocNumber='" + identificationDocNumber + '\'' +
                ", status=" + status +
                '}';
    }
}
