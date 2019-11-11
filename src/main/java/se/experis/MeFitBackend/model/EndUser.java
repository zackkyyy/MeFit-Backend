package se.experis.MeFitBackend.model;

<<<<<<< HEAD
import org.jboss.aerogear.security.otp.api.Base32;
=======
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
>>>>>>> 140ac13c165b3ab8fbad7ba3e8745333a872e054

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

    private String secret;

    public EndUser() {
    }

    public EndUser(int endUserId, String password) {
        this.endUserId = endUserId;
        this.password = password;
    }

    public EndUser(int endUserId, String firstname, String lastname, String email) {
        this.endUserId = endUserId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.secret = Base32.random();
    }

    public int getEndUserId() {
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
