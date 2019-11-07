package se.experis.MeFitBackend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

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
  //  @JsonManagedReference
    @JoinColumn(name="profile_fk")
    private List<Goal> goalFk = new ArrayList<Goal>();

    @OneToMany
    //@JsonManagedReference
    @JoinColumn(name="profile_fk")
    private List<Program> programFk = new ArrayList<Program>();

    @OneToMany
    @JoinColumn(name="profile_fk")
    private List<Workout> workoutFk = new ArrayList<Workout>();

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

    public List<Workout> getWorkoutFk() {
        return workoutFk;
    }

    public List<Goal> getGoalFk() {
        return goalFk;
    }

    public List<Program> getProgramFk() {
        return programFk;
    }
}
