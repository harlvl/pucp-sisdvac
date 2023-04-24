package edu.pucp.sisdvac.controller;

import edu.pucp.sisdvac.controller.dto.TestSubjectDto;
import org.springframework.http.ResponseEntity;

public interface ITestSubjectController {
    ResponseEntity<?> findAll();

    ResponseEntity<?> findById(Integer id);
    ResponseEntity<?> save(TestSubjectDto dto);
    ResponseEntity<?> update(TestSubjectDto dto);

}
