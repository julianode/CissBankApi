package com.cissbank.basiccissbankapi.entity;

import java.util.Objects;

public class Address {

    private int id;
    private String streetName;
    private String streetNumber;
    private String complement;
    private String neighbourhood;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    public Address() {}

    public Address(String streetName, String streetNumber, String complement, String neighbourhood, String city,
                   String state, String country, String zipCode) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.complement = complement;
        this.neighbourhood = neighbourhood;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return id == address.id && Objects.equals(streetName, address.streetName)
                && Objects.equals(streetNumber, address.streetNumber) && Objects.equals(complement, address.complement)
                && Objects.equals(neighbourhood, address.neighbourhood) && Objects.equals(city, address.city)
                && Objects.equals(state, address.state) && Objects.equals(country, address.country)
                && Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, streetName, streetNumber, complement, neighbourhood, city, state, country, zipCode);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", complement='" + complement + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
