package edu.pucp.sisdvac.controller;

import edu.pucp.sisdvac.controller.dto.FormulationDto;
import edu.pucp.sisdvac.controller.dto.FormulationEvaluationDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.domain.FormulationEvaluation;
import org.springframework.http.ResponseEntity;

public interface ITrialController {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Integer id);
    ResponseEntity<?> findByInsNumber(String key);
    ResponseEntity<?> save(TrialDto dto);
    ResponseEntity<?> update(Integer id, TrialDto dto);
    ResponseEntity<?> updateFormulation(Integer id, Integer key, FormulationDto dto);
    ResponseEntity<?> addFormulation(Integer id, FormulationDto dto);
    ResponseEntity<?> evaluateFormulation(Integer id, Integer formulationId, FormulationEvaluationDto formulationEvaluation);
}
