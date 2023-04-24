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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements IVolunteerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VolunteerServiceImpl.class);
    private final VolunteerRepository volunteerRepository;

    @Override
    public List<VolunteerDto> findAll() {
        List<TestVolunteer> dbItems = volunteerRepository.findAll();
        List<VolunteerDto> response = new ArrayList<>();
        for (TestVolunteer dbItem:
             dbItems) {
            response.add(VolunteerParser.toDto(dbItem));
        }
        return response;
    }

    @Override
    public VolunteerDto save(VolunteerDto volunteerDto){
        LOGGER.info("Saving volunteer...");
        return VolunteerParser.toDto(
                volunteerRepository.save(
                        VolunteerParser.fromDto(volunteerDto)
                )
        );
    }

    @Override
    public VolunteerDto update(VolunteerDto dto) {
        LOGGER.info("Updating volunteer");

        TestVolunteer dbItem = volunteerRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Volunteer %d not found.", dto.getId()))
                );

        return VolunteerParser.toDto(
                volunteerRepository.save(
                        BaseParser.copyProperties(dto, dbItem)
                )
        );
    }
}
