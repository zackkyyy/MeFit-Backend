package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Goal;
import se.experis.MeFitBackend.model.GoalWorkout;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

public interface GoalWorkoutRepository extends JpaRepository<GoalWorkout, Integer> {
}