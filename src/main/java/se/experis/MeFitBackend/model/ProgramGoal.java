package se.experis.MeFitBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
    rjanul created on 2019-11-07
*/
@Entity
@Table
public class ProgramGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int programGoalId;

    @Column
    private boolean complete;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="program_fk")
    private Program programFk;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="goal_fk")
    private Goal goalFk;

    @OneToMany(orphanRemoval=true)
    @JoinColumn(name="program_goal_fk")
    private List<GoalWorkout> goalWorkoutFk = new ArrayList<GoalWorkout>();

    public ProgramGoal() {
    }

    public ProgramGoal(boolean complete, Goal goalFk, Program programFk) {
        this.complete = complete;
        this.goalFk = goalFk;
        this.programFk = programFk;
    }

    public ProgramGoal(int programGoalId, boolean complete, Goal goalFk, Program programFk) {
        this.programGoalId = programGoalId;
        this.complete = complete;
        this.programFk = programFk;
        this.goalFk = goalFk;
    }

    public int getProgramGoalId() {
        return programGoalId;
    }

    public boolean isComplete() {
        return complete;
    }

    public Program getProgramFk() {
        return programFk;
    }

    public Goal getGoalFk() {
        return goalFk;
    }

    public List<GoalWorkout> getGoalWorkoutFk() {
        return goalWorkoutFk;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void setProgramFk(Program programFk) {
        this.programFk = programFk;
    }

    public void setGoalFk(Goal goalFk) {
        this.goalFk = goalFk;
    }
}
