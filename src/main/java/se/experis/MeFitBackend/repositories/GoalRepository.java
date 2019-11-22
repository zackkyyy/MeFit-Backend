package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Address;
import se.experis.MeFitBackend.model.Goal;
import se.experis.MeFitBackend.model.Profile;

import java.util.List;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    List<Goal> findByProfileFkAndAchieved(Profile profileFk, boolean achieved);
}
