package edu.pucp.sisdvac.controller.impl;

import edu.pucp.sisdvac.controller.ICandidateVaccineController;
import edu.pucp.sisdvac.controller.dto.CandidateVaccineDto;
import edu.pucp.sisdvac.controller.response.RestResponse;
import edu.pucp.sisdvac.service.ICandidateVaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/candidate-vaccine")
@RequiredArgsConstructor
public class CandidateVaccineControllerImpl implements ICandidateVaccineController {
    private final ICandidateVaccineService service;


    @Override
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<?> dtos = service.findAll();
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
    public ResponseEntity<?> save(@Valid @RequestBody CandidateVaccineDto dto) {
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
    public ResponseEntity<?> update(@Valid @RequestBody CandidateVaccineDto dto) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(service.update(dto))
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
