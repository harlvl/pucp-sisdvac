package edu.pucp.sisdvac.dao;

import edu.pucp.sisdvac.domain.Trial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrialRepository extends JpaRepository<Trial, Integer> {
    Optional<Trial> findByInsNumber(String insNumber);
}
