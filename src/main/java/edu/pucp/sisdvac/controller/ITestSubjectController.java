package edu.pucp.sisdvac.controller;

import edu.pucp.sisdvac.controller.dto.TestSubjectDto;
import org.springframework.http.ResponseEntity;

public interface ITestSubjectController {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Integer id);
    ResponseEntity<?> findByCodeName(String key);
    ResponseEntity<?> save(TestSubjectDto dto);
    ResponseEntity<?> update(TestSubjectDto dto);

}
