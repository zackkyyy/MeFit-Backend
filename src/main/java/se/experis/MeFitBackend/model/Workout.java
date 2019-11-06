package se.experis.MeFitBackend.model;

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
    public int workoutId;

    @Column
    public String name;

    @Column
    public String type;

    @Column
    public Boolean complete;

    @Column
    public int repetitions;

    @OneToMany
    @JsonManagedReference
    @JoinColumn(name = "workout_fk")
    private List<Set> set = new ArrayList<Set>();


    public Workout() {
    }

    public Workout(String name, String type, Boolean complete, int repetitions) {
        this.name = name;
        this.type = type;
        this.complete = complete;
        this.repetitions = repetitions;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Boolean getComplete() {
        return complete;
    }

    public int getRepetitions() {
        return repetitions;
    }
    public int getWorkoutId() {
        return workoutId;
    }

    public List<Set> getSet() {
        return set;
    }
}
