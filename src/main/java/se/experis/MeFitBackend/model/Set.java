package se.experis.MeFitBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    @JoinColumn(name="exercise_fk")
    private Exercise exerciseFk;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="workout_fk")
    private Workout workoutFk;

    public Set() {
    }

    public Set(int repetitions, Exercise exerciseFk, Workout workoutFk) {
        this.repetitions = repetitions;
        this.exerciseFk = exerciseFk;
        this.workoutFk = workoutFk;
    }

    public int getSetId() {
        return setId;
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
