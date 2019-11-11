package se.experis.MeFitBackend.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    public EndUser(int endUserId, String password) {
        this.endUserId = endUserId;
        this.password = password;
    }

    public EndUser(int endUserId, String firstname, String lastname, String email) {
        this.endUserId = endUserId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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

    public void setRole(int role) {
        this.role = role;
    }
}
