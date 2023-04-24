package edu.pucp.sisdvac.service;

import edu.pucp.sisdvac.controller.dto.VolunteerDto;
import edu.pucp.sisdvac.domain.TestVolunteer;

import java.util.List;

public interface IVolunteerService {
    String testing();
    List<VolunteerDto> getVolunteers();

    VolunteerDto saveVolunteer(VolunteerDto volunteerDto);

    VolunteerDto updateVolunteer(VolunteerDto volunteerDto);
}
