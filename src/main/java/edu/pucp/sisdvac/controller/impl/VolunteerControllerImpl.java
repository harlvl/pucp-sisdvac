package edu.pucp.sisdvac.controller.impl;

import edu.pucp.sisdvac.controller.IVolunteerController;
import edu.pucp.sisdvac.controller.dto.VolunteerDto;
import edu.pucp.sisdvac.controller.response.RestResponse;
import edu.pucp.sisdvac.service.IVolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/volunteer")
@RequiredArgsConstructor
public class VolunteerControllerImpl implements IVolunteerController {
    private final IVolunteerService service;
    @Override
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<VolunteerDto> volunteerDtos = service.findAll();
        return ResponseEntity.ok().body(RestResponse.builder()
                .timestamp(LocalDateTime.now())
                .payload(volunteerDtos)
                .build()
        );
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok().body(RestResponse.builder()
                .timestamp(LocalDateTime.now())
                .payload(service.findById(id))
                .hits(1)
                .build()
        );
    }

    @Override
    @GetMapping("/document_number/{document_number}")
    public ResponseEntity<?> findByDocumentNumber(@PathVariable(name = "document_number") final String key) {
        return ResponseEntity.ok().body(RestResponse.builder()
                .timestamp(LocalDateTime.now())
                .payload(service.findByDocumentNumber(key))
                .hits(1)
                .build()
        );
    }

    @Override
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody VolunteerDto volunteerDto) {
        return ResponseEntity.ok().body(RestResponse.builder()
                .timestamp(LocalDateTime.now())
                .payload(service.save(volunteerDto))
                .build());
    }

    @Override
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody VolunteerDto volunteerDto) {
        return ResponseEntity.ok().body(RestResponse.builder()
                .timestamp(LocalDateTime.now())
                .payload(service.update(volunteerDto))
                .build());
    }
}
