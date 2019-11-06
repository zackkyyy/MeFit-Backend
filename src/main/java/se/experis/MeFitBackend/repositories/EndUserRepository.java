package se.experis.MeFitBackend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.EndUser;

public interface EndUserRepository extends JpaRepository<EndUser, Integer> {

}
