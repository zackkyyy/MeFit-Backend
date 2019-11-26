package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.*;

import java.util.List;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

public interface GoalWorkoutRepository extends JpaRepository<GoalWorkout, String> {
    List<GoalWorkout> findAllByGoalFk(Goal goalFk);
    GoalWorkout findByProgramGoalFkAndWorkoutFk(ProgramGoal programGoalFk, Workout workoutFk);
    GoalWorkout findByGoalFkAndWorkoutFkAndProgramGoalFk(Goal goalFk, Workout workoutFk, ProgramGoal programGoalFk);
    GoalWorkout findTopByWorkoutFk(Workout WorkoutFk);

}
