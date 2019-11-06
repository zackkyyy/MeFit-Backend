package se.experis.MeFitBackend.model;

import javax.persistence.*;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */
@Entity
@Table
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int setId;

    @Column
    private int repetitions;

    @OneToOne
    @JoinColumn(name = "exerciseFk")
    private Exercise exerciseFk;

    @OneToOne
    @JoinColumn(name = "workoutFk")
    private Workout workoutFk;

    public Set() {
    }

    public Set(int repetitions, Exercise exerciseFk, Workout workoutFk) {
        this.repetitions = repetitions;
        this.exerciseFk = exerciseFk;
        this.workoutFk = workoutFk;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public Exercise getExerciseFk() {
        return exerciseFk;
    }

    public Workout getWorkoutFk() {
        return workoutFk;
    }
}
