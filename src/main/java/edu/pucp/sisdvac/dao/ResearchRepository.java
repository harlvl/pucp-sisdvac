package edu.pucp.sisdvac.dao;

import edu.pucp.sisdvac.domain.Research;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResearchRepository extends JpaRepository<Research, Integer> {
    Optional<Research> findByInsNumber(String insNumber);
}
