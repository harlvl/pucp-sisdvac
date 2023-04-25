package edu.pucp.sisdvac.service;

import edu.pucp.sisdvac.controller.dto.CandidateVaccineDto;
import java.util.List;

public interface ICandidateVaccineService {
    List<CandidateVaccineDto> findAll();
    CandidateVaccineDto findById(Integer id);
    CandidateVaccineDto findByCode(String key);
    CandidateVaccineDto save(CandidateVaccineDto dto);
    CandidateVaccineDto update(CandidateVaccineDto dto);
}
