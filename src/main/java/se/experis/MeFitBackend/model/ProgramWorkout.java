package se.experis.MeFitBackend.model;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */
@Entity
@Table
public class ProgramWorkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int programWorkoutId;

    @ManyToOne
    @JoinColumn(name="program_fk")
    private Program programFk;

    @OneToOne
    @JoinColumn(name = "workout_fk")
    private Workout workoutFk;


    public ProgramWorkout(Program programFk, Workout workoutFk) {
        this.programFk = programFk;
        this.workoutFk = workoutFk;
    }

    public ProgramWorkout() {
    }

    public int getProgramWorkoutId() {
        return programWorkoutId;
    }

    public Program getProgramFk() {
        return programFk;
    }

    public Workout getWorkoutFk() {
        return workoutFk;
    }
}
