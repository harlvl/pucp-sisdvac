package edu.pucp.sisdvac.dao;

import edu.pucp.sisdvac.domain.InfectiousDisease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfectiousDiseaseRepository extends JpaRepository<InfectiousDisease, Integer> {
}
