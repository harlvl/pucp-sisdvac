package edu.pucp.sisdvac.service;

import edu.pucp.sisdvac.controller.dto.*;
import edu.pucp.sisdvac.controller.request.AnimalStudyEvaluationRequest;

import java.util.List;

public interface ITrialService {
    List<TrialDto> findAll();
    TrialDto findById(Integer id);
    TrialDto findByInsNumber(String key);
    TrialDto save(TrialDto dto);
    TrialDto update(Integer id, TrialDto dto);
    TrialDto addFormulation(Integer id, FormulationDto dto);
    Object evaluateFormulation(Integer id, Integer formulationId, FormulationEvaluationDto formulationEvaluation);
    Object findFormulationEvaluation(Integer tid, Integer fid);
    Object saveAdvance(Integer trialId, AdvanceDto dto);
    Object saveAnimalStudy(Integer tid, AnimalStudyDto dto);
    Object evaluateAnimalStudy(Integer tid, Integer aid, AnimalStudyEvaluationRequest dto);
}
