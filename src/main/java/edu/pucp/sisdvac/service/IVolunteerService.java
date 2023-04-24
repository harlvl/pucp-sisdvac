package edu.pucp.sisdvac.service;

import edu.pucp.sisdvac.controller.dto.VolunteerDto;

import java.util.List;

public interface IVolunteerService {
    List<VolunteerDto> findAll();

    VolunteerDto save(VolunteerDto volunteerDto);

    VolunteerDto update(VolunteerDto volunteerDto);
}
