package se.experis.MeFitBackend.model;

import javax.persistence.*;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

@Entity
@Table
public class ProfileGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profileGoalId;

    @ManyToOne
    @JoinColumn(name = "profile_fk")
    private Profile profileFk;

    @OneToOne
    @JoinColumn(name = "goal_fk")
    private Goal goalFk;

    public ProfileGoal(Profile profileFk, Goal goalFk) {
        this.profileFk = profileFk;
        this.goalFk = goalFk;
    }

    public ProfileGoal() {
    }

    public int getProfileGoalId() {
        return profileGoalId;
    }

    public Profile getProfileFk() {
        return profileFk;
    }

    public Goal getGoalFk() {
        return goalFk;
    }

}
