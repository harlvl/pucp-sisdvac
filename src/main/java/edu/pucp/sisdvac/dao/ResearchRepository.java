package edu.pucp.sisdvac.dao;

import edu.pucp.sisdvac.domain.Research;
import edu.pucp.sisdvac.domain.Trial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResearchRepository extends JpaRepository<Research, Integer> {
    List<Research> findAll();
    Optional<Research> findById(Integer integer);
    Optional<Research> findByInsNumber(String insNumber);
}
