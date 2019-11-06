package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
}
