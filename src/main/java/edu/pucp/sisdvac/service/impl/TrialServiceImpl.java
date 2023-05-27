package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.AdvanceDto;
import edu.pucp.sisdvac.controller.dto.AnimalStudyDto;
import edu.pucp.sisdvac.controller.dto.FormulationDto;
import edu.pucp.sisdvac.controller.dto.FormulationEvaluationDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.dao.TrialRepository;
import edu.pucp.sisdvac.dao.parser.AdvanceParser;
import edu.pucp.sisdvac.dao.parser.AnimalStudyParser;
import edu.pucp.sisdvac.dao.parser.BaseParser;
import edu.pucp.sisdvac.dao.parser.FormulationEvaluationParser;
import edu.pucp.sisdvac.dao.parser.FormulationParser;
import edu.pucp.sisdvac.dao.parser.TrialParser;
import edu.pucp.sisdvac.domain.Advance;
import edu.pucp.sisdvac.domain.EvaluationItem;
import edu.pucp.sisdvac.domain.Formulation;
import edu.pucp.sisdvac.domain.FormulationEvaluation;
import edu.pucp.sisdvac.domain.Trial;
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
    private final TrialRepository repository;
    @Override
    public List<TrialDto> findAll() {
        List<Trial> dbItems = repository.findAll();
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
                repository.findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "Trial [%d] not found", id
                        )))
        );
    }

    @Override
    public TrialDto findByInsNumber(String key) {
        return TrialParser.toDto(
                repository.findByInsNumber(key)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "Trial with INS number [%s] not found", key
                        )))
        );
    }

    @Override
    public TrialDto save(TrialDto dto) {
        LOGGER.info("Creating new trial...");
        return TrialParser.toDto(
                repository.save(
                        TrialParser.fromDto(dto)
                )
        );
    }

    @Override
    public TrialDto update(Integer id, TrialDto dto) {
        LOGGER.info(String.format(
                "Updating existing Trial [%d]...", id)
        );
        Trial dbItem = repository.findById(id)
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
                repository.save(
                        BaseParser.copyProperties(dto, dbItem)
                )
        );
    }

    @Override
    public TrialDto addFormulation(Integer id, FormulationDto dto) {
        LOGGER.info(String.format(
                "Adding new formulation to existing trial [%d]...", id)
        );
        Trial dbItem = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Trial [%d] not found.", id)
                ));

        // TODO validate it's not null
        Collection<Formulation> updatedFormulations = FormulationParser.updateFormulations(dbItem.getFormulations(), dto);

        dbItem.setFormulations(updatedFormulations);
        return TrialParser.toDto(
                repository.save(dbItem)
        );
    }

    @Override
    public Object evaluateFormulation(Integer id, Integer formulationId, FormulationEvaluationDto dto) {
        LOGGER.info(String.format(
                "Evaluating formulation [%d] for trial [%d]...",
                formulationId,
                id)
        );
        Trial dbItem = repository.findById(id)
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

        Trial savedItem = repository.save(dbItem);
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
        Trial dbItem = repository.findById(tid)
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

        Trial dbItem = repository.findById(tid)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Trial [%d] not found.", tid)
                ));

        Advance advanceToCreate = AdvanceParser.fromDto(dto);
        if (dbItem.getAdvances() == null) {
            dbItem.setAdvances(new ArrayList<>());
        }

        dbItem.getAdvances().add(advanceToCreate);

        return TrialParser.toDto(repository.save(dbItem));
    }

    @Override
    public Object saveAnimalStudy(Integer tid, AnimalStudyDto dto) {
        LOGGER.info(String.format("Saving animal study for trial [%d]...", tid));

        Trial dbItem = repository.findById(tid)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Trial [%d] not found.", tid)
                ));

        // create advance and animal study
        Advance advance = Advance.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .stage(Stage.PRECLINICAL)
                .animalStudy(AnimalStudyParser.fromDto(dto))
                .createdAt(new Date())
                .lastUpdatedAt(new Date())
                .build();

        // save trial
        if (dbItem.getAdvances() == null) {
            dbItem.setAdvances(new ArrayList<>());
        }

        dbItem.getAdvances().add(advance);


        return TrialParser.toDto(repository.save(dbItem));
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
