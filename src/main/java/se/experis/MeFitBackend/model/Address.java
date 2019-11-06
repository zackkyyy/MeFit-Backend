package se.experis.MeFitBackend.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

@Entity
@Table
public class Address {
    /** */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private int postalCode;

    @OneToMany
    @JoinColumn(name="address_fk")
    private List<Profile> profileFk = new ArrayList<Profile>();

    public Address() {
    }

    public Address(String street, String city, String country, int postalCode) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

    public int getId() {
        return addressId;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public int getPostalCode() {
        return postalCode;
    }
}

