package se.experis.MeFitBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */
@Table
@Entity
public class ProfileWorkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profileWorkoutId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="profile_fk")
    private Profile profileFk;

    @OneToOne
    @JoinColumn(name = "workout_fk")
    private Workout workoutFk;

    public ProfileWorkout(Profile profileFk, Workout workoutFk) {
        this.profileFk = profileFk;
        this.workoutFk = workoutFk;
    }

    public ProfileWorkout() {
    }

    public int getProfileWorkoutId() {
        return profileWorkoutId;
    }

    public Profile getProfileFk() {
        return profileFk;
    }

    public Workout getWorkoutFk() {
        return workoutFk;
    }
}
