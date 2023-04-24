package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.CandidateVaccineDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.dao.CandidateVaccineRepository;
import edu.pucp.sisdvac.dao.parser.BaseParser;
import edu.pucp.sisdvac.dao.parser.CandidateVaccineParser;
import edu.pucp.sisdvac.domain.CandidateVaccine;
import edu.pucp.sisdvac.service.ICandidateVaccineService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateVaccineImpl implements ICandidateVaccineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CandidateVaccineImpl.class);
    private final CandidateVaccineRepository repository;

    @Override
    public List<CandidateVaccineDto> findAll() {
        List<CandidateVaccine> dbItems = repository.findAll();
        List<CandidateVaccineDto> response = new ArrayList<>();
        for (CandidateVaccine dbItem :
                dbItems) {
            response.add(CandidateVaccineParser.toDto(dbItem));
        }
        return response;
    }

    @Override
    public CandidateVaccineDto findById(Integer id) {
        return CandidateVaccineParser.toDto(
                repository.findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "Candidate vaccine [%d] not found", id
                        )))
        );
    }

    @Override
    public CandidateVaccineDto save(CandidateVaccineDto dto) {
        LOGGER.info("Creating new vaccine candidate...");
        return CandidateVaccineParser.toDto(
                repository.save(
                        CandidateVaccineParser.fromDto(dto)
                )
        );
    }

    @Override
    public CandidateVaccineDto update(CandidateVaccineDto dto) {
        LOGGER.info(String.format(
                "Updating existing vaccine candidate [%d]...", dto.getId())
        );
        CandidateVaccine dbItem = repository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Vaccine candidate [%d] not found.", dto.getId())
                ));

        return CandidateVaccineParser.toDto(
                repository.save(
                        BaseParser.copyProperties(dto, dbItem)
                )
        );
    }
}
