package se.experis.MeFitBackend.model;

import javax.persistence.*;

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

    @OneToOne
    @JoinColumn(name = "workoutFk" , insertable = false ,updatable = false)
    private Set setFk;

    @OneToOne
    @JoinColumn(name = "workoutFk")
    private ProfileWorkout profileWorkoutFk;

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

    public Set getSetFk() {
        return setFk;
    }
}
