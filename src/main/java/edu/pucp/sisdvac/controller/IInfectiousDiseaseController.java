package edu.pucp.sisdvac.controller;

import edu.pucp.sisdvac.controller.dto.InfectiousDiseaseDto;
import org.springframework.http.ResponseEntity;

public interface IInfectiousDiseaseController {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Integer id);
    ResponseEntity<?> save(InfectiousDiseaseDto dto);
    ResponseEntity<?> update(InfectiousDiseaseDto dto);
}
