package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.VolunteerDto;
import edu.pucp.sisdvac.dao.VolunteerRepository;
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
    private final VolunteerRepository volunteerRepository;

    @Override
    public String testing() {
        return "testing";
    }

    @Override
    public List<VolunteerDto> getVolunteers() {
        List<TestVolunteer> volunteers = volunteerRepository.findAll();
        List<VolunteerDto> response = new ArrayList<>();
        for (TestVolunteer volunteer:
             volunteers) {
            VolunteerDto volunteerDto = new VolunteerDto();
            try {
                volunteerDto = VolunteerParser.toDto(volunteer);
                response.add(volunteerDto);
            } catch (Exception e) {
                LOGGER.error(String.format("Could not parse volunteer %d: %s %s", volunteer.getId(), volunteer.getFirstName(), volunteer.getLastName()));
            }

        }
        return response;
    }

    @Override
    public VolunteerDto saveVolunteer(VolunteerDto volunteerDto){
        LOGGER.info("Saving volunteer...");
        VolunteerDto response = new VolunteerDto();
        try {
            TestVolunteer savedVolunteer = volunteerRepository.save(VolunteerParser.fromDto(volunteerDto));
            response = VolunteerParser.toDto(savedVolunteer);
        } catch (Exception e) {
            LOGGER.error("Error saving volunteer");
        }

        return response;
    }
}
