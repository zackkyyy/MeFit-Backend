package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Profile;
import se.experis.MeFitBackend.model.ProfileGoal;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

public interface ProfileGoalRepository extends JpaRepository<ProfileGoal, Integer> {
}
