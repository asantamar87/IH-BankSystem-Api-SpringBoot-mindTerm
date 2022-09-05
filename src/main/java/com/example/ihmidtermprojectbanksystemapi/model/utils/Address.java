package com.example.ihmidtermprojectbanksystemapi.model.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Address {

    private int houseNumber;
    private String streetName;
    private String postcode;
    private String city;
    private String country;

    public Address(int houseNumber, String streetName, String postcode, String city, String country) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.postcode = postcode;
        this.city = city;
        this.country = country;
    }
}