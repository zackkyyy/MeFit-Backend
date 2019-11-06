package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
}
