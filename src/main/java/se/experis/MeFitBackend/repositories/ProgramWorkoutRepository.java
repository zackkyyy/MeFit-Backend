package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Program;
import se.experis.MeFitBackend.model.ProgramWorkout;
import se.experis.MeFitBackend.model.Workout;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

public interface ProgramWorkoutRepository extends JpaRepository<ProgramWorkout, String> {
    Integer deleteByProgramFk(Program programFk);
    ProgramWorkout findTopByWorkoutFk(Workout WorkoutFk);
}
