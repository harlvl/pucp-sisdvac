package edu.pucp.sisdvac.dao;

import edu.pucp.sisdvac.domain.AnimalStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalStudyRepository extends JpaRepository<AnimalStudy, Integer> {
}
