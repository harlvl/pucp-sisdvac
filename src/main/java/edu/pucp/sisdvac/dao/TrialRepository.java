package edu.pucp.sisdvac.dao;

import edu.pucp.sisdvac.domain.Trial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrialRepository extends JpaRepository<Trial, Integer> {
    List<Trial> findAll();
    Optional<Trial> findById(Integer integer);
}
