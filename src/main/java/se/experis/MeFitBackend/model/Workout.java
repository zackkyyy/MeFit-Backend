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

@Table
@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int workoutId;

    @Column
    private String name;

    @Column
    private String type;

    @OneToMany(orphanRemoval=true)
    @JsonManagedReference
    @JoinColumn(name = "workout_fk")
    private List<Set> set = new ArrayList<Set>();

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="profile_fk")
    private Profile profileFk;


    public Workout() {
    }

    public Workout(String name, String type, Profile profileFk) {
        this.name = name;
        this.type = type;
        this.profileFk = profileFk;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<Set> getSet() {
        return set;
    }

    public Profile getProfileFk() {
        return profileFk;
    }

    public void setProfileFk(Profile profileFk) {
        this.profileFk = profileFk;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
