package se.experis.MeFitBackend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column
    private int role;

    @Column(unique=true, nullable=false)
    private String userId;

    @OneToMany(orphanRemoval=true)
    @JoinColumn(name="profile_fk")
    private List<Goal> goalFk = new ArrayList<Goal>();

    @OneToMany
    @JoinColumn(name="profile_fk")
    private List<Program> programFk = new ArrayList<Program>();

    @OneToMany
    @JoinColumn(name="profile_fk")
    private List<Workout> workoutFk = new ArrayList<Workout>();

    @OneToOne
    @JoinColumn(name="address_fk")
    private Address addressFk;

    public Profile() {
    }

    public Profile(int profileId, int weight, int height, int age, String fitnessLevel) {
        this.profileId = profileId;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.fitnessLevel = fitnessLevel;
    }

    public Profile(int weight, int height, int age, String fitnessLevel, String userId, Address addressFk) {
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.fitnessLevel = fitnessLevel;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
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

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFitnessLevel(String fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAddressFk(Address addressFk) {
        this.addressFk = addressFk;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
