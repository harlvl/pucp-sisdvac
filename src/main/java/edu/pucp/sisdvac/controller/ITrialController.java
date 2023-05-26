package edu.pucp.sisdvac.controller;

import edu.pucp.sisdvac.controller.dto.AdvanceDto;
import edu.pucp.sisdvac.controller.dto.FormulationDto;
import edu.pucp.sisdvac.controller.dto.FormulationEvaluationDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;
import org.springframework.http.ResponseEntity;

public interface ITrialController {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Integer id);
    ResponseEntity<?> findByInsNumber(String key);
    ResponseEntity<?> save(TrialDto dto);
    ResponseEntity<?> update(Integer id, TrialDto dto);

//    FORMULATION HANDLING
    ResponseEntity<?> updateFormulation(Integer id, Integer key, FormulationDto dto);
    ResponseEntity<?> addFormulation(Integer id, FormulationDto dto);
    ResponseEntity<?> evaluateFormulation(Integer id, Integer formulationId, FormulationEvaluationDto formulationEvaluation);
    ResponseEntity<?> findFormulationEvaluation(Integer trialId, Integer formulationId);

    //    ADVANCES HANDLING
    // ANIMAL STUDIES METHODS
    ResponseEntity<?> saveAdvance(Integer trialId, AdvanceDto dto);
}
