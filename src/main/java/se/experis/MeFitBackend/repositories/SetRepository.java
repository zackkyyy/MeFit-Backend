package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Set;
import se.experis.MeFitBackend.model.Workout;

public interface SetRepository extends JpaRepository<Set, Integer> {
    Integer deleteByWorkoutFk(Workout workoutFk);
}
