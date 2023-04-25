package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.dao.TrialRepository;
import edu.pucp.sisdvac.dao.parser.BaseParser;
import edu.pucp.sisdvac.dao.parser.TrialParser;
import edu.pucp.sisdvac.domain.Trial;
import edu.pucp.sisdvac.service.ITrialService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        return TrialParser.toDto(
                repository.save(
                        BaseParser.copyProperties(dto, dbItem)
                )
        );
    }
}
