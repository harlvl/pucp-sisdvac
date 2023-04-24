package edu.pucp.sisdvac.service;

import edu.pucp.sisdvac.controller.dto.InfectiousDiseaseDto;

import java.util.List;

public interface IInfectiousDiseaseService{
    List<InfectiousDiseaseDto> findAll();
    InfectiousDiseaseDto findById(Integer id);
    InfectiousDiseaseDto save(InfectiousDiseaseDto dto);
    InfectiousDiseaseDto update(InfectiousDiseaseDto dto);
}
