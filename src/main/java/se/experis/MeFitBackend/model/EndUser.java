package se.experis.MeFitBackend.model;

import javax.persistence.*;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */


@Entity
@Table
public class EndUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int endUserId;

    @Column
    public String firstname;

    @Column
    public String lastname;

    @Column
    public String password;

    @Column
    public int role;

    @Column(unique=true)
    public String email;


    public EndUser() {
    }

    public EndUser(String firstname, String lastname, String password, int role, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public int getId() {
        return endUserId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }
}