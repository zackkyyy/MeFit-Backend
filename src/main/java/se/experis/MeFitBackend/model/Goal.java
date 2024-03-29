package se.experis.MeFitBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String goalId;

    @Column
    private String name;

    @Column
    private Boolean achieved;

    @Column
    private Date startDate;

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

    public Goal(String name, Boolean achieved, Date startDate, Date endDate, Profile profileFk) {
        this.name = name;
        this.achieved = achieved;
        this.startDate = startDate;
        this.endDate = endDate;
        this.profileFk = profileFk;
    }

    public Goal(String goalId, Boolean achieved, Date endDate, Profile profileFk) {
        this.goalId = goalId;
        this.achieved = achieved;
        this.endDate = endDate;
        this.profileFk = profileFk;
    }

    public Goal() {
    }

    public String getGoalId() {
        return goalId;
    }

    public String getName() {
        return name;
    }

    public Boolean getAchieved() {
        return achieved;
    }

    public Date getStartDate() {
        return startDate;
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

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAchieved(Boolean achieved) {
        this.achieved = achieved;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setProfileFk(Profile profileFk) {
        this.profileFk = profileFk;
    }
}
