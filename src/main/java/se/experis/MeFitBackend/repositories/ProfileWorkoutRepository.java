package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.ProfileWorkout;

public interface ProfileWorkoutRepository  extends JpaRepository<ProfileWorkout, Integer> {
}
