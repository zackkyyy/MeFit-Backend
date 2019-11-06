package se.experis.MeFitBackend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Profile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profileId;

    @Column
    private int weight;

    @Column
    private int height;

    @Column
    private int age;

    @Column
    private String fitnessLevel;

    @OneToOne
    @JoinColumn(name="end_user_fk")
    private EndUser endUserFk;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="address_fk")
    private Address addressFk;

    @OneToMany
    @JsonManagedReference
    @JoinColumn(name="profile_fk")
    private List<ProfileWorkout> profileWorkoutsFk  = new ArrayList<ProfileWorkout>();

    public Profile() {
    }

    public Profile(int weight, int height, int age, String fitnessLevel, EndUser endUserFk, Address addressFk) {
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.fitnessLevel = fitnessLevel;
        this.endUserFk = endUserFk;
        this.addressFk = addressFk;
    }


    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public int getAge() {
        return age;
    }

    public String getFitnessLevel() {
        return fitnessLevel;
    }

    public EndUser getEndUserFk() {
        return endUserFk;
    }

    public Address getAddressFk() {
        return addressFk;
    }

    public List<ProfileWorkout> getProfileWorkoutsFk() {
        return profileWorkoutsFk;
    }
}
