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

    @OneToMany
    @JsonManagedReference
    @JoinColumn(name="profile_fk")
    private List<ProfileGoal> profileGoalFk = new ArrayList<ProfileGoal>();

    @OneToMany
    @JsonManagedReference
    @JoinColumn(name="profile_fk")
    private List<ProgramProfile> programProfileFk = new ArrayList<ProgramProfile>();

    @OneToMany
    @JsonManagedReference
    @JoinColumn(name="profile_fk")
    private List<ProfileWorkout> profileWorkoutFk  = new ArrayList<ProfileWorkout>();

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="address_fk")
    private Address addressFk;


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

    public int getProfileId() {
        return profileId;
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

    public List<ProfileGoal> getProfileGoalFk() {
        return profileGoalFk;
    }

    public List<ProgramProfile> getProgramProfileFk() {
        return programProfileFk;
    }

    public List<ProfileWorkout> getProfileWorkoutFk() {
        return profileWorkoutFk;
    }
}
