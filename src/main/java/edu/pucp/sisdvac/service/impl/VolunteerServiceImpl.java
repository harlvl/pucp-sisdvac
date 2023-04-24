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
        TestVolunteer savedVolunteer = volunteerRepository.save(VolunteerParser.fromDto(volunteerDto));
        return VolunteerParser.toDto(savedVolunteer);
    }

    @Override
    public VolunteerDto updateVolunteer(VolunteerDto volunteerDto) {
        LOGGER.info("Updating volunteer");

        TestVolunteer testVolunteer = volunteerRepository.findById(volunteerDto.getId()).orElseThrow(() -> new NotFoundException("Volunteer not found."));
        LOGGER.info(String.format("Found volunteer: %d) %s|%s %s", testVolunteer.getId(), testVolunteer.getDocumentNumber(), testVolunteer.getFirstName(), testVolunteer.getLastName()));

        TestVolunteer updatedVolunteer = BaseParser.copyProperties(volunteerDto, testVolunteer);

//        testVolunteer.setDocumentNumber(volunteerDto.getDocumentNumber());
//        testVolunteer.setEmail(volunteerDto.getEmail());
//        testVolunteer.setContactNumber(volunteerDto.getContactNumber());
//        testVolunteer.setFirstName(volunteerDto.getFirstName());
//        testVolunteer.setLastName(volunteerDto.getLastName());

        return VolunteerParser.toDto(volunteerRepository.save(updatedVolunteer));
    }
}
