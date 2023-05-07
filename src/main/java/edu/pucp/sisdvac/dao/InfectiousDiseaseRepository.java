package edu.pucp.sisdvac.dao;

import edu.pucp.sisdvac.domain.InfectiousDisease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InfectiousDiseaseRepository extends JpaRepository<InfectiousDisease, Integer> {
}
