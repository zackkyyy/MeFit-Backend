package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Goal;
import se.experis.MeFitBackend.model.ProgramGoal;

import java.util.List;

public interface ProgramGoalRepository extends JpaRepository<ProgramGoal, Integer> {
}
