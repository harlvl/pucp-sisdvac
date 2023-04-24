package edu.pucp.sisdvac.controller;

import edu.pucp.sisdvac.controller.dto.InfectiousDiseaseDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;
import org.springframework.http.ResponseEntity;

public interface ITrialController {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Integer id);
    ResponseEntity<?> save(TrialDto dto);
    ResponseEntity<?> update(Integer id, TrialDto dto);
}
