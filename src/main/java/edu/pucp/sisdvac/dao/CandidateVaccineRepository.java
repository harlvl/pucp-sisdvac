package edu.pucp.sisdvac.dao;

import edu.pucp.sisdvac.domain.CandidateVaccine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateVaccineRepository extends JpaRepository<CandidateVaccine, Integer> {
    Optional<CandidateVaccine> findByCode(String code);
}
