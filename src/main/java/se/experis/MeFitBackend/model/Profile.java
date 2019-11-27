package se.experis.MeFitBackend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.net.URL;
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
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String profileId;

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
    @Column(length = 1024)
    private URL profileImage;

    @Column(unique = true, nullable = false)
    private String userId;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "profile_fk")
    private List<Goal> goalFk = new ArrayList<Goal>();

    @OneToMany
    @JoinColumn(name = "profile_fk")
    private List<Program> programFk = new ArrayList<Program>();

    @OneToMany
    @JoinColumn(name = "profile_fk")
    private List<Workout> workoutFk = new ArrayList<Workout>();

    @OneToOne
    @JoinColumn(name = "address_fk")
    private Address addressFk;

    public Profile() {
    }

    public Profile(String profileId, int weight, int height, int age, String fitnessLevel) {
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

    public String getProfileId() {
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

    public int getRole() {
        return role;
    }

    public URL getProfileImage() {
        return profileImage;
    }

    public String getUserId() {
        return userId;
    }

    public List<Goal> getGoalFk() {
        return goalFk;
    }

    public List<Program> getProgramFk() {
        return programFk;
    }

    public List<Workout> getWorkoutFk() {
        return workoutFk;
    }

    public Address getAddressFk() {
        return addressFk;
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

    public void setRole(int role) {
        this.role = role;
    }

    public void setProfileImage(URL profileImage) {
        this.profileImage = profileImage;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAddressFk(Address addressFk) {
        this.addressFk = addressFk;
    }
}
