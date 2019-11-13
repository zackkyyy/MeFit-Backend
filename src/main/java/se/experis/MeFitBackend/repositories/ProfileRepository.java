package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Profile;

import java.util.List;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    List<Profile> findAllByAddressFk(int addressFk);
}
