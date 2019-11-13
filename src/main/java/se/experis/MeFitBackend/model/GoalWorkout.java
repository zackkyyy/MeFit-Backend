package se.experis.MeFitBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */
@Entity
@Table
public class GoalWorkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int goalWorkoutId;

    @Column
    private boolean complete;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="goal_fk")
    private Goal goalFk;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="program_goal_fk")
    private ProgramGoal programGoalFk;

    @OneToOne
    @JoinColumn(name = "workout_fk")
    private Workout workoutFk;

    public GoalWorkout(int goalWorkoutId, boolean complete, Goal goalFk, ProgramGoal programGoalFk, Workout workoutFk) {
        this.goalWorkoutId = goalWorkoutId;
        this.complete = complete;
        this.goalFk = goalFk;
        this.programGoalFk = programGoalFk;
        this.workoutFk = workoutFk;
    }

    public GoalWorkout(int goalWorkoutId, boolean complete, Goal goalFk, Workout workoutFk) {
        this.goalWorkoutId = goalWorkoutId;
        this.complete = complete;
        this.goalFk = goalFk;
        this.workoutFk = workoutFk;
    }

    public GoalWorkout(boolean complete, ProgramGoal programGoalFk, Workout workoutFk) {
        this.complete = complete;
        this.programGoalFk = programGoalFk;
        this.workoutFk = workoutFk;
    }

    public GoalWorkout(boolean complete, Goal goalFk, Workout workoutFk) {
        this.complete = complete;
        this.goalFk = goalFk;
        this.workoutFk = workoutFk;
    }

    public GoalWorkout() {
    }

    public boolean isComplete() {
        return complete;
    }

    public int getGoalWorkoutId() {
        return goalWorkoutId;
    }

    public Goal getGoalFk() {
        return goalFk;
    }

    public Workout getWorkoutFk() {
        return workoutFk;
    }

    public ProgramGoal getProgramGoalFk() {
        return programGoalFk;
    }
}
