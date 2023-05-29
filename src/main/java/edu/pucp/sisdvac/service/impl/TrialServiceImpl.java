package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.*;
import edu.pucp.sisdvac.controller.exception.FormulaCalculationException;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.controller.request.AnimalStudyEvaluationRequest;
import edu.pucp.sisdvac.dao.AdvanceRepository;
import edu.pucp.sisdvac.dao.AnimalStudyRepository;
import edu.pucp.sisdvac.dao.TrialRepository;
import edu.pucp.sisdvac.dao.parser.*;
import edu.pucp.sisdvac.domain.*;
import edu.pucp.sisdvac.domain.enums.EvaluationFormulaEnum;
import edu.pucp.sisdvac.domain.enums.Stage;
import edu.pucp.sisdvac.service.ITrialService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TrialServiceImpl implements ITrialService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrialServiceImpl.class);
    private final TrialRepository trialRepository;
    private final AdvanceRepository advanceRepository;
    private final AnimalStudyRepository animalStudyRepository;

    @Override
    public List<TrialDto> findAll() {
        List<Trial> dbItems = trialRepository.findAll();
        List<TrialDto> response = new ArrayList<>();
        for (Trial dbItem :
                dbItems) {
            response.add(TrialParser.toDto(dbItem));
        }
        return response;
    }

    @Override
    public TrialDto findById(Integer id) {
        return TrialParser.toDto(
                trialRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "Trial [%d] not found", id
                        )))
        );
    }

    @Override
    public TrialDto findByInsNumber(String key) {
        return TrialParser.toDto(
                trialRepository.findByInsNumber(key)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "Trial with INS number [%s] not found", key
                        )))
        );
    }

    @Override
    public TrialDto save(TrialDto dto) {
        LOGGER.info("Creating new trial...");
        return TrialParser.toDto(
                trialRepository.save(
                        TrialParser.fromDto(dto)
                )
        );
    }

    @Override
    public TrialDto update(Integer id, TrialDto dto) {
        LOGGER.info(String.format(
                "Updating existing Trial [%d]...", id)
        );
        Trial dbItem = trialRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Trial [%d] not found.", id)
                ));

        if (dto.getTpp() != null) {
            if (dto.getTpp().getItems().size() != dbItem.getTargetProductProfile().getItems().size()) {
                LOGGER.info(String.format(
                        "Target product profile items length has changed from %d to %d",
                        dbItem.getTargetProductProfile().getItems().size(),
                        dto.getTpp().getItems().size()
                ));
                dbItem.setTargetProductProfile(TrialParser.updateTpp(dto.getTpp()));
            }
        }

        if (dto.getFormulations() != null && !dto.getFormulations().isEmpty()) {
            List<FormulationDto> formulations = dto.getFormulations();
            for (FormulationDto item :
                    formulations) {
                //TODO UPDATE FORMULATIONS
                if (item.getItems() != null && !item.getItems().isEmpty()) {

                }
            }
        }

//        if (dto.getFormulation() != null) {
//            if (dto.getFormulation().getItems().size() != dbItem.getFormulation().getItems().size()) {
//                LOGGER.info(String.format(
//                        "Formulation items length has changed from %d to %d",
//                        dbItem.getFormulation().getItems().size(),
//                        dto.getFormulation().getItems().size()
//                ));
//                dbItem.setFormulation(TrialParser.updateFormulation(dto.getFormulation()));
//            }
//        }

        return TrialParser.toDto(
                trialRepository.save(
                        BaseParser.copyProperties(dto, dbItem)
                )
        );
    }

    @Override
    public TrialDto addFormulation(Integer id, FormulationDto dto) {
        LOGGER.info(String.format(
                "Adding new formulation to existing trial [%d]...", id)
        );
        Trial dbItem = trialRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Trial [%d] not found.", id)
                ));

        // TODO validate it's not null
        Collection<Formulation> updatedFormulations = FormulationParser.updateFormulations(dbItem.getFormulations(), dto);

        dbItem.setFormulations(updatedFormulations);
        return TrialParser.toDto(
                trialRepository.save(dbItem)
        );
    }

    @Override
    public Object evaluateFormulation(Integer id, Integer formulationId, FormulationEvaluationDto dto) {
        LOGGER.info(String.format(
                "Evaluating formulation [%d] for trial [%d]...",
                formulationId,
                id)
        );
        Trial dbItem = trialRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Trial [%d] not found.", id)
                ));

        // get formulation details
        Formulation formulationToEvaluate = new Formulation();
        for (Formulation f :
                dbItem.getFormulations()) {
            if (Objects.equals(f.getId(), formulationId)) {
                formulationToEvaluate = f;
                break;
            }
        }

        // calculate values
        Map<String, BigDecimal> calculatedValues = calculateFormulas(dto);

        List<EvaluationItem> items = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> set : calculatedValues.entrySet()) {
            items.add(
                    EvaluationItem.builder()
                            .type(EvaluationFormulaEnum.valueOf(set.getKey()))
                            .detail(set.getValue())
                            .build()
            );
        }

        formulationToEvaluate.setEvaluation(
                FormulationEvaluation.builder()
                        .items(items)
                        .build()
        );

        List<Formulation> formulations = (List<Formulation>) dbItem.getFormulations();

        // update formulation by adding the evaluation
        for (int i = 0; i < formulations.size(); i++) {
            if (Objects.equals(formulations.get(i).getId(), formulationToEvaluate.getId())) {
                formulations.set(i, formulationToEvaluate);
                break;
            }
        }

        dbItem.setFormulations(formulations);

        Trial savedItem = trialRepository.save(dbItem);
        List<Formulation> updatedFormulations = (List<Formulation>) savedItem.getFormulations();

        Formulation result = new Formulation();
        for (Formulation f :
                updatedFormulations) {
            if (Objects.equals(f.getId(), formulationId)) {
                result = f;
                break;
            }
        }

        return FormulationEvaluationDto.builder()
                .id(result.getId())
                .items(FormulationParser.getItems(result.getEvaluation().getItems()))
                .build();
    }

    @Override
    public Object findFormulationEvaluation(Integer tid, Integer fid) {
        LOGGER.info(String.format(
                "Finding formulation [%d] for trial [%d]...",
                fid,
                tid)
        );
        Trial dbItem = trialRepository.findById(tid)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Trial [%d] not found.", tid)
                ));

        boolean formulationFoundFlag = false;
        Formulation formulationFound = new Formulation();
        for (Formulation f :
                dbItem.getFormulations()) {
            if (Objects.equals(f.getId(), fid)) {
                formulationFoundFlag = true;
                formulationFound = f;
                break;
            }
        }

        if (!formulationFoundFlag) {
            throw new NotFoundException(String.format(
                    "Formulation [%d] not found.", fid
            ));
        }

        LOGGER.info(String.format("Formulation found: %s", formulationFound));

        if (formulationFound.getEvaluation() == null) {
            throw new NotFoundException(String.format(
                    "Formulation [%d] has not been evaluated.", fid
            ));
        }

        return FormulationEvaluationParser.toDto(formulationFound.getEvaluation());
    }

    @Override
    public Object saveAdvance(Integer tid, AdvanceDto dto) {
        LOGGER.info(String.format("Saving advance for trial [%d]...", tid));

        Trial dbItem = trialRepository.findById(tid)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Trial [%d] not found.", tid)
                ));

        Advance advanceToCreate = AdvanceParser.fromDto(dto);
        if (dbItem.getAdvances() == null) {
            dbItem.setAdvances(new ArrayList<>());
        }

        dbItem.getAdvances().add(advanceToCreate);

        return TrialParser.toDto(trialRepository.save(dbItem));
    }

    @Override
    public Object saveAnimalStudy(Integer tid, AnimalStudyDto dto) {
        LOGGER.info(String.format("Saving animal study for trial [%d]...", tid));

        Trial dbItem = trialRepository.findById(tid)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Trial [%d] not found.", tid)
                ));

        // create advance and animal study
        Advance advance = Advance.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .stage(Stage.PRECLINICAL)
                .animalStudy(AnimalStudyParser.fromDto(dto))
                .startDate(new Date())
                .createdAt(new Date())
                .lastUpdatedAt(new Date())
                .build();

        // save trial
        if (dbItem.getAdvances() == null) {
            dbItem.setAdvances(new ArrayList<>());
        }

        dbItem.getAdvances().add(advance);


        return TrialParser.toDto(trialRepository.save(dbItem));
    }

    @Override
    public Object evaluateAnimalStudy(Integer tid, Integer aid, AnimalStudyEvaluationRequest request) {
        LOGGER.info(String.format("Evaluating animal study for trial [%d] and advance [%d]..."
                , tid, aid));

        Trial dbItem = trialRepository.findById(tid)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Trial [%d] not found.", tid)
                ));

        Advance advanceDb = advanceRepository.findById(aid)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Advance [%d] not found.", aid)
                ));

        AnimalStudy animalStudyDb = advanceDb.getAnimalStudy();
        if (animalStudyDb == null) {
            throw new NotFoundException(String.format(
                    "Advance [%d] has null animal study.", aid
            ));
        }


        // calculate formulas
        Map<String, BigDecimal> calculatedValues = calculateFormulas(request);

        animalStudyDb.setEvaluation(AnimalStudyEvaluationParser.fromMap(calculatedValues));

        LOGGER.info("Saving evaluation...");
        AnimalStudy savedElement = animalStudyRepository.save(animalStudyDb);
        return AnimalStudyParser.toDto(savedElement);
    }

    private Map<String, BigDecimal> calculateFormulas(AnimalStudyEvaluationRequest request) {
        LOGGER.info("Calculating formulas...");
        Map<String, BigDecimal> response = new HashMap<>();

        try {
            response.put(
                    String.valueOf(EvaluationFormulaEnum.EFFICACY),
                    calculateEfficacy(request)
            );
            response.put(
                    String.valueOf(EvaluationFormulaEnum.EFFICIENCY),
                    calculateEfficiency(request)
            );
            response.put(
                    String.valueOf(EvaluationFormulaEnum.SAFETY_INDEX),
                    calculateSafetyIndex(request)
            );
            response.put(
                    String.valueOf(EvaluationFormulaEnum.INCIDENCE_RATE),
                    calculateIncidenceRate(request)
            );
            response.put(
                    String.valueOf(EvaluationFormulaEnum.IMMUNOGENICITY),
                    calculateImmunogenicity(request)
            );
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Error calculating formula: %s", e.getMessage()
            ));
            throw new FormulaCalculationException(e.getMessage());
        }

        return response;
    }

    private BigDecimal calculateImmunogenicity(AnimalStudyEvaluationRequest request) {
        return BigDecimal.valueOf(
                (request.getAnimalsWithDetectableImmuneResponse() / request.getTotalVaccinatedAnimals()) * 100L
        );
    }

    private BigDecimal calculateIncidenceRate(AnimalStudyEvaluationRequest request) {
        if (request.getAdverseEventsVaccinatedGroup().equals(0)) {
            return new BigDecimal(0);
        }

        return BigDecimal.valueOf(
                (request.getAdverseEventsVaccinatedGroup() / request.getTotalVaccinatedAnimals()) * 100L
        );
    }

    private BigDecimal calculateSafetyIndex(AnimalStudyEvaluationRequest request) {
        return BigDecimal.valueOf(
                request.getLethalDose() / request.getEffectiveDose()
        );
    }

    private BigDecimal calculateEfficiency(AnimalStudyEvaluationRequest request) {
        return BigDecimal.valueOf(
                (request.getAttackRateUnvaccinatedGroup() - request.getAttackRateVaccinatedGroup())
                        / request.getAttackRateUnvaccinatedGroup()
        );
    }

    private BigDecimal calculateEfficacy(AnimalStudyEvaluationRequest request) {
        return BigDecimal.valueOf(
                (request.getAttackRateUnvaccinatedGroup() - request.getAttackRateVaccinatedGroup())
                        / request.getAttackRateUnvaccinatedGroup()
        );
    }

    private Map<String, BigDecimal> calculateFormulas(FormulationEvaluationDto dto) {
        Map<String, BigDecimal> response = new HashMap<>();

        try {
            response.put(String.valueOf(EvaluationFormulaEnum.IMMUNOGENICITY), BigDecimal.valueOf(5.1));
            response.put(String.valueOf(EvaluationFormulaEnum.EFFICACY), BigDecimal.valueOf(2.3));
            response.put(String.valueOf(EvaluationFormulaEnum.EFFICIENCY), BigDecimal.valueOf(3.4));
            response.put(String.valueOf(EvaluationFormulaEnum.SAFETY_INDEX), BigDecimal.valueOf(1.2));
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Error calculating formula: %s", e.getMessage()
            ));
        }

        return response;
    }
}
