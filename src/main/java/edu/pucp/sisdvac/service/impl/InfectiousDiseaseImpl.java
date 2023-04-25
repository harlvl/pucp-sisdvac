package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.InfectiousDiseaseDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.dao.InfectiousDiseaseRepository;
import edu.pucp.sisdvac.dao.parser.BaseParser;
import edu.pucp.sisdvac.dao.parser.InfectiousDiseaseParser;
import edu.pucp.sisdvac.domain.InfectiousDisease;
import edu.pucp.sisdvac.service.IInfectiousDiseaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InfectiousDiseaseImpl implements IInfectiousDiseaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InfectiousDiseaseImpl.class);
    private final InfectiousDiseaseRepository repository;
    @Override
    public List<InfectiousDiseaseDto> findAll() {
        List<InfectiousDisease> dbItems = repository.findAll();
        List<InfectiousDiseaseDto> response = new ArrayList<>();
        for (InfectiousDisease dbItem :
                dbItems) {
            response.add(InfectiousDiseaseParser.toDto(dbItem));
        }
        return response;
    }

    @Override
    public InfectiousDiseaseDto findById(Integer id) {
        return InfectiousDiseaseParser.toDto(
                repository.findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "Infectious disease [%d] not found", id
                        )))
        );
    }

    @Override
    public InfectiousDiseaseDto save(InfectiousDiseaseDto dto) {
        LOGGER.info("Creating new infectious disease...");
        return InfectiousDiseaseParser.toDto(
                repository.save(
                        InfectiousDiseaseParser.fromDto(dto)
                )
        );
    }

    @Override
    public InfectiousDiseaseDto update(InfectiousDiseaseDto dto) {
        LOGGER.info(String.format(
                "Updating existing vaccine candidate [%d]...", dto.getId())
        );
        InfectiousDisease dbItem = repository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Vaccine candidate [%d] not found.", dto.getId())
                ));

        return InfectiousDiseaseParser.toDto(
                repository.save(
                        BaseParser.copyProperties(dto, dbItem)
                )
        );
    }
}
