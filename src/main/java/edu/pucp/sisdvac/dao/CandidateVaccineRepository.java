package edu.pucp.sisdvac.dao;

import edu.pucp.sisdvac.domain.CandidateVaccine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateVaccineRepository extends JpaRepository<CandidateVaccine, Integer> {
    List<CandidateVaccine> findAll();
    Optional<CandidateVaccine> findById(Integer integer);
}
