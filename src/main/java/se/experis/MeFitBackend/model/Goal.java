package se.experis.MeFitBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int goalId;

    @Column
    private Boolean achieved;

    @Column
    private Date endDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="profile_fk")
    private Profile profileFk;

    @OneToMany(orphanRemoval=true)
    @JoinColumn(name="goal_fk")
    private List<GoalWorkout> goalWorkoutFk = new ArrayList<GoalWorkout>();

    @OneToMany(orphanRemoval=true)
    @JoinColumn(name="goal_fk")
    private List<ProgramGoal> programGoalFk = new ArrayList<ProgramGoal>();

    public Goal(Boolean achieved, Date endDate, Profile profileFk) {
        this.achieved = achieved;
        this.endDate = endDate;
        this.profileFk = profileFk;
    }

    public Goal(int goalId, Boolean achieved, Date endDate, Profile profileFk) {
        this.goalId = goalId;
        this.achieved = achieved;
        this.endDate = endDate;
        this.profileFk = profileFk;
    }

    public Goal() {
    }

    public int getGoalId() {
        return goalId;
    }

    public Boolean getAchieved() {
        return achieved;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Profile getProfileFk() {
        return profileFk;
    }

    public List<GoalWorkout> getGoalWorkoutFk() {
        return goalWorkoutFk;
    }

    public List<ProgramGoal> getProgramGoalFk() {
        return programGoalFk;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public void setAchieved(Boolean achieved) {
        this.achieved = achieved;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setProfileFk(Profile profileFk) {
        this.profileFk = profileFk;
    }
}
