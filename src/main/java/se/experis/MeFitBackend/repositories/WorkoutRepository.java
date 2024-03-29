package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Profile;
import se.experis.MeFitBackend.model.Workout;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, String> {
    List<Workout> findAllByProfileFk(Profile profileFk);
}
