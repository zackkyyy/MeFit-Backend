package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Program;
import se.experis.MeFitBackend.model.ProgramWorkout;

import java.util.List;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

public interface ProgramRepository extends JpaRepository<Program, Integer> {
    List<Program> findAllByGoalFk(int goalFk);
}
