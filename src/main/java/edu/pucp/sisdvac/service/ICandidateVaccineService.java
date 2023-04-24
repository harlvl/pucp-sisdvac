package edu.pucp.sisdvac.service;

import edu.pucp.sisdvac.controller.dto.CandidateVaccineDto;
import edu.pucp.sisdvac.controller.dto.TestSubjectDto;

import java.util.List;

public interface ICandidateVaccineService {
    List<CandidateVaccineDto> findAll();
    CandidateVaccineDto findById(Integer id);
    CandidateVaccineDto save(CandidateVaccineDto dto);
    CandidateVaccineDto update(CandidateVaccineDto dto);
}
