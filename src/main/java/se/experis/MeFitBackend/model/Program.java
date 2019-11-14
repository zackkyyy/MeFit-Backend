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
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int programId;

    @Column
    private String name;

    @Column
    private String category;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="profile_fk")
    private Profile profileFk;

    @OneToMany(orphanRemoval=true)
    @JoinColumn(name="program_fk")
    private List<ProgramGoal> programGoalFk = new ArrayList<ProgramGoal>();

    @OneToMany(orphanRemoval=true)
    @JoinColumn(name="program_fk")
    private List<ProgramWorkout> programWorkoutFk = new ArrayList<ProgramWorkout>();


    public Program(String name, String category, Profile profileFk) {
        this.name = name;
        this.category = category;
        this.profileFk = profileFk;
    }

    public Program() {
    }

    public int getProgramId() {
        return programId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Profile getProfileFk() {
        return profileFk;
    }

    public List<ProgramGoal> getProgramGoalFk() {
        return programGoalFk;
    }

    public List<ProgramWorkout> getProgramWorkoutFk() {
        return programWorkoutFk;
    }

    public void setProfileFk(Profile profileFk) {
        this.profileFk = profileFk;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}


