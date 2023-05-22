package edu.pucp.sisdvac.service;

import edu.pucp.sisdvac.controller.dto.FormulationDto;
import edu.pucp.sisdvac.controller.dto.FormulationEvaluationDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;

import java.util.List;

public interface ITrialService {
    List<TrialDto> findAll();
    TrialDto findById(Integer id);
    TrialDto findByInsNumber(String key);
    TrialDto save(TrialDto dto);
    TrialDto update(Integer id, TrialDto dto);
    TrialDto addFormulation(Integer id, FormulationDto dto);
    Object evaluateFormulation(Integer id, Integer formulationId, FormulationEvaluationDto formulationEvaluation);
}
