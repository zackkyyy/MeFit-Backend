package se.experis.MeFitBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/*
    rjanul created on 2019-11-07
*/
@Entity
@Table
public class ProgramGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int programGoalId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="program_fk")
    private Program programFk;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="goal_fk")
    private Goal goalFk;


    public ProgramGoal() {
    }

    public ProgramGoal(Goal goalFk, Program programFk) {
        this.goalFk = goalFk;
        this.programFk = programFk;
    }

    public int getProgramGoalId() {
        return programGoalId;
    }

    public Program getProgramFk() {
        return programFk;
    }

    public Goal getGoalFk() {
        return goalFk;
    }
}
