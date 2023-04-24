package edu.pucp.sisdvac.controller.impl;

import edu.pucp.sisdvac.controller.ITrialController;
import edu.pucp.sisdvac.controller.dto.InfectiousDiseaseDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.controller.response.RestResponse;
import edu.pucp.sisdvac.service.ITrialService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/infectious-disease")
@RequiredArgsConstructor
public class TrialController implements ITrialController {
    private final ITrialService service;
    @Override
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<TrialDto> dtos = service.findAll();
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
    public ResponseEntity<?> findById(@PathVariable(name = "id") final Integer id) {
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
    public ResponseEntity<?> save(@Valid @RequestBody TrialDto dto) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(service.save(dto))
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") final Integer id, @Valid @RequestBody TrialDto dto) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(service.update(id, dto))
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
