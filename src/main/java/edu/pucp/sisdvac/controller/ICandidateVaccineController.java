package edu.pucp.sisdvac.controller;

import edu.pucp.sisdvac.controller.dto.CandidateVaccineDto;
import org.springframework.http.ResponseEntity;

public interface ICandidateVaccineController {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Integer id);
    ResponseEntity<?> save(CandidateVaccineDto dto);
    ResponseEntity<?> update(CandidateVaccineDto dto);
}
