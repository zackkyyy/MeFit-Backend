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
    private Date endDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="goal_fk")
    private Goal goalFk;

    @OneToOne
    @JoinColumn(name = "workout_fk")
    private Workout workoutFk;

    public GoalWorkout(Date endDate, Goal goalFk, Workout workoutFk) {
        this.endDate = endDate;
        this.goalFk = goalFk;
        this.workoutFk = workoutFk;
    }

    public GoalWorkout() {
    }

    public int getId() {
        return goalWorkoutId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Goal getGoalFk() {
        return goalFk;
    }

    public Workout getWorkoutFk() {
        return workoutFk;
    }
}
