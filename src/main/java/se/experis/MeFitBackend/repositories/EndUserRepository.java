package se.experis.MeFitBackend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.EndUser;
import se.experis.MeFitBackend.model.GoalWorkout;

import java.util.List;

public interface EndUserRepository extends JpaRepository<EndUser, Integer> {
    EndUser findByEmail(String email);
}
