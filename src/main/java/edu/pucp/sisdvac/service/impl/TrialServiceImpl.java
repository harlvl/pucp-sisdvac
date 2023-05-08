package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.FormulationDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.dao.TrialRepository;
import edu.pucp.sisdvac.dao.parser.BaseParser;
import edu.pucp.sisdvac.dao.parser.TrialParser;
import edu.pucp.sisdvac.domain.Formulation;
import edu.pucp.sisdvac.domain.Trial;
import edu.pucp.sisdvac.service.ITrialService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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


        return null;
    }
}
