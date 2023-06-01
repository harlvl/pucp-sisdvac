package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.AdvanceDto;
import edu.pucp.sisdvac.controller.dto.AnimalStudyDto;
import edu.pucp.sisdvac.controller.dto.FormulationDto;
import edu.pucp.sisdvac.controller.dto.FormulationEvaluationDto;
import edu.pucp.sisdvac.controller.dto.GenericStudyDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.controller.request.AnimalStudyEvaluationRequest;
import edu.pucp.sisdvac.dao.AdvanceRepository;
import edu.pucp.sisdvac.dao.AnimalStudyRepository;
import edu.pucp.sisdvac.dao.TrialRepository;
import edu.pucp.sisdvac.dao.parser.AdvanceParser;
import edu.pucp.sisdvac.dao.parser.BaseParser;
import edu.pucp.sisdvac.dao.parser.FormulationEvaluationParser;
import edu.pucp.sisdvac.dao.parser.FormulationParser;
import edu.pucp.sisdvac.dao.parser.GenericStudyEvaluationParser;
import edu.pucp.sisdvac.dao.parser.GenericStudyParser;
import edu.pucp.sisdvac.dao.parser.TrialParser;
import edu.pucp.sisdvac.domain.Advance;
import edu.pucp.sisdvac.domain.AnimalStudy;
import edu.pucp.sisdvac.domain.EvaluationItem;
import edu.pucp.sisdvac.domain.Formulation;
import edu.pucp.sisdvac.domain.FormulationEvaluation;
import edu.pucp.sisdvac.domain.GenericStudy;
import edu.pucp.sisdvac.domain.Trial;
import edu.pucp.sisdvac.domain.enums.EvaluationFormulaEnum;
import edu.pucp.sisdvac.domain.enums.Stage;
import edu.pucp.sisdvac.service.ITrialService;
import edu.pucp.sisdvac.service.calculator.Calculator;
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
        Map<String, BigDecimal> calculatedValues = Calculator.calculateFormulas(dto);

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
                .startDate(dto.getStartDate() != null ? dto.getStartDate(): new Date())
                .stage(Stage.PRECLINICAL)
                .animalStudy(GenericStudyParser.fromDto(dto))
                .createdAt(new Date())
                .lastUpdatedAt(new Date())
                .build();

        // save trial
        if (dbItem.getAdvances() == null) {
            LOGGER.info("Initializing advances collection...");
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
        Map<String, BigDecimal> calculatedValues = Calculator.calculateFormulas(request);

        animalStudyDb.setEvaluation(GenericStudyEvaluationParser.fromMap(calculatedValues));

        LOGGER.info("Saving evaluation...");
        AnimalStudy savedElement = animalStudyRepository.save(animalStudyDb);
        return GenericStudyParser.toDto(savedElement);
    }

    @Override
    public Object saveClinicalStudy(Integer tid, Integer aid, GenericStudyDto dto) {
        LOGGER.info(String.format("Saving clinical study for trial [%d]...", tid));

        Trial dbItem = trialRepository.findById(tid)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Trial [%d] not found.", tid)
                ));

        if (aid == null) { // there are no advances yet, the first one must be created
            LOGGER.info("No advances associated, creating first one...");
            Collection<GenericStudy> studies = new ArrayList<>();
            studies.add(GenericStudyParser.fromDto(dto));

            Advance advance = Advance.builder()
                    .startDate(dto.getStartDate() != null ? dto.getStartDate(): new Date())
                    .stage(Stage.CLINICAL)
                    .studies(studies)
                    .createdAt(new Date())
                    .lastUpdatedAt(new Date())
                    .build();

            if (dbItem.getAdvances() == null) {
                LOGGER.info("Initializing advances collection...");
                dbItem.setAdvances(new ArrayList<>());
            }

            dbItem.getAdvances().add(advance);
        } else { // there already exists at least 1 advance
            LOGGER.info(String.format("Searching advance [%d]...", aid));
            Advance advance = advanceRepository.findById(aid)
                    .orElseThrow(() -> new NotFoundException(String.format(
                            "Advance [%d] not found.", aid)
                    ));

            if (advance.getStage() != Stage.CLINICAL) {
                throw new IllegalStateException(String.format("Advance [%d] is a preclinical study. Please input a clinical stage advance.", aid));
            }

            List<Advance> dbItemAdvances = (List<Advance>) dbItem.getAdvances();
            Advance advanceToUpdate = new Advance();
            int indexFound = -1;
            for (int i = 0; i < dbItemAdvances.size(); i++) {
                Advance item = dbItemAdvances.get(i);
                if (item.getId().equals(aid)) {
                    advanceToUpdate = item;
                    indexFound = i;
                    break;
                }
            }

            if (indexFound == -1) {
                throw new IllegalStateException(String.format(
                        "Advance [%d] does NOT belong to trial [%d]", aid, tid
                ));
            }

            Collection<GenericStudy> studies = advanceToUpdate.getStudies();

            studies.add(GenericStudyParser.fromDto(dto));
            advanceToUpdate.setStudies(studies);

            dbItemAdvances.set(indexFound, advanceToUpdate);
            dbItem.setAdvances(dbItemAdvances);
        }

        return TrialParser.toDto(trialRepository.save(dbItem));
    }

}
