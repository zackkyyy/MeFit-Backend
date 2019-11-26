package se.experis.MeFitBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String setId;

    @Column
    private int repetitions;

    @Column
    private int setRepetitions;

    @OneToOne
    @JoinColumn(name="exercise_fk")
    private Exercise exerciseFk;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="workout_fk")
    private Workout workoutFk;

    public Set() {
    }


    public Set(int repetitions, int setRepetitions, Exercise exerciseFk, Workout workoutFk) {
        this.repetitions = repetitions;
        this.setRepetitions = setRepetitions;
        this.exerciseFk = exerciseFk;
        this.workoutFk = workoutFk;
    }

    public String getSetId() {
        return setId;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public int getSetRepetitions() {
        return setRepetitions;
    }

    public Exercise getExerciseFk() {
        return exerciseFk;
    }

    public Workout getWorkoutFk() {
        return workoutFk;
    }

}
