package edu.pucp.sisdvac.service;

import edu.pucp.sisdvac.controller.dto.VolunteerDto;

import java.util.List;

public interface IVolunteerService {
    List<VolunteerDto> findAll();
    VolunteerDto findById(Integer id);
    VolunteerDto findByDocumentNumber(String key);
    VolunteerDto save(VolunteerDto volunteerDto);
    VolunteerDto update(VolunteerDto volunteerDto);
}
