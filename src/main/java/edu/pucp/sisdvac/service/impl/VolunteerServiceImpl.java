package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.VolunteerDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.dao.VolunteerRepository;
import edu.pucp.sisdvac.dao.parser.BaseParser;
import edu.pucp.sisdvac.dao.parser.VolunteerParser;
import edu.pucp.sisdvac.domain.TestVolunteer;
import edu.pucp.sisdvac.service.IVolunteerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements IVolunteerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VolunteerServiceImpl.class);
    private final VolunteerRepository repository;

    @Override
    public List<VolunteerDto> findAll() {
        List<TestVolunteer> dbItems = repository.findAll();
        List<VolunteerDto> response = new ArrayList<>();
        for (TestVolunteer dbItem:
             dbItems) {
            response.add(VolunteerParser.toDto(dbItem));
        }
        return response;
    }

    @Override
    public VolunteerDto findById(Integer id) {
        return VolunteerParser.toDto(
                repository.findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "Volunteer with ID [%d] not found.", id
                        )))
        );
    }

    @Override
    public VolunteerDto findByDocumentNumber(String key) {
        return VolunteerParser.toDto(
                repository.findByDocumentNumber(key)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "Volunteer with document number [%s] not found.", key
                        )))
        );
    }

    @Override
    public VolunteerDto save(VolunteerDto dto){
        LOGGER.info("Creating new volunteer...");
        return VolunteerParser.toDto(
                repository.save(
                        VolunteerParser.fromDto(dto)
                )
        );
    }

    @Override
    public VolunteerDto update(VolunteerDto dto) {
        LOGGER.info("Updating existing volunteer...");

        TestVolunteer dbItem = repository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Volunteer %d not found.", dto.getId()))
                );

        return VolunteerParser.toDto(
                repository.save(
                        BaseParser.copyProperties(dto, dbItem)
                )
        );
    }
}
