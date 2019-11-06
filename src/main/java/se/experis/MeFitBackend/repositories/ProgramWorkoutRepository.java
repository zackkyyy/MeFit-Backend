package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.GoalWorkout;
import se.experis.MeFitBackend.model.ProgramWorkout;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

public interface ProgramWorkoutRepository extends JpaRepository<ProgramWorkout, Integer> {
}
