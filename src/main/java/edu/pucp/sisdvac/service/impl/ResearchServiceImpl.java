package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.ResearchDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.dao.ResearchRepository;
import edu.pucp.sisdvac.dao.parser.BaseParser;
import edu.pucp.sisdvac.dao.parser.ResearchParser;
import edu.pucp.sisdvac.dao.parser.TrialParser;
import edu.pucp.sisdvac.domain.Research;
import edu.pucp.sisdvac.domain.Trial;
import edu.pucp.sisdvac.service.IResearchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResearchServiceImpl implements IResearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResearchServiceImpl.class);
    private final ResearchRepository repository;
    @Override
    public List<ResearchDto> findAll() {
        List<Research> dbItems = repository.findAll();
        List<ResearchDto> response = new ArrayList<>();
        for (Research dbItem :
                dbItems) {
            response.add(ResearchParser.toDto(dbItem));
        }
        return response;
    }

    @Override
    public ResearchDto findById(Integer id) {
        return ResearchParser.toDto(
                repository.findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "Research [%d] not found", id
                        )))
        );
    }

    @Override
    public ResearchDto findByInsNumber(String key) {
        return ResearchParser.toDto(
                repository.findByInsNumber(key)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "Research with INS number [%s] not found", key
                        )))
        );
    }

    @Override
    public ResearchDto save(ResearchDto dto) {
        LOGGER.info("Creating new research...");
        return ResearchParser.toDto(
                repository.save(
                        ResearchParser.fromDto(dto)
                )
        );
    }

    @Override
    public ResearchDto update(Integer id, ResearchDto dto) {
        LOGGER.info(String.format(
                "Updating existing Trial [%d]...", id)
        );
        Research dbItem = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Research [%d] not found.", id)
                ));

        return ResearchParser.toDto(
                repository.save(
                        BaseParser.copyProperties(dto, dbItem)
                )
        );
    }
}
