package edu.pucp.sisdvac.controller.impl;

import edu.pucp.sisdvac.controller.ITestSubjectController;
import edu.pucp.sisdvac.controller.dto.TestSubjectDto;
import edu.pucp.sisdvac.controller.response.RestResponse;
import edu.pucp.sisdvac.service.ITestSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/test-subject")
@RequiredArgsConstructor
public class TestSubjectControllerImpl implements ITestSubjectController {
    private final ITestSubjectService service;
    @Override
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<TestSubjectDto> dtos = service.findAll();
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(dtos)
                        .hits(dtos.size())
                        .build()
        );
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") final Integer id) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(service.findById(id))
                        .hits(1)
                        .build()
        );
    }

    @Override
    @PostMapping
    public ResponseEntity<?> save(@RequestBody final TestSubjectDto dto) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(service.save(dto))
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @Override
    @PutMapping
    public ResponseEntity<?> update(@RequestBody final TestSubjectDto dto) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(service.update(dto))
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
