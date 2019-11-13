package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Workout;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    List<Workout> findAllByProfileFk(int profileFk);
}
