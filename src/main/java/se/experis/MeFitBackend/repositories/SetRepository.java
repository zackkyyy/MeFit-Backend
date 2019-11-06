package se.experis.MeFitBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.MeFitBackend.model.Set;

public interface SetRepository extends JpaRepository<Set, Integer> {
}
