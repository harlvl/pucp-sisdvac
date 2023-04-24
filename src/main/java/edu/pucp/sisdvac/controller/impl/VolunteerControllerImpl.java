package edu.pucp.sisdvac.controller.impl;

import edu.pucp.sisdvac.controller.IVolunteerController;
import edu.pucp.sisdvac.controller.dto.VolunteerDto;
import edu.pucp.sisdvac.controller.response.RestResponse;
import edu.pucp.sisdvac.service.IVolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/volunteer")
@RequiredArgsConstructor
public class VolunteerControllerImpl implements IVolunteerController {
    private final IVolunteerService volunteerService;
    @Override
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<VolunteerDto> volunteerDtos = volunteerService.findAll();
        return ResponseEntity.ok().body(RestResponse.builder().timestamp(LocalDateTime.now()).payload(volunteerDtos).build());
    }

    @Override
    @PostMapping
    public ResponseEntity<?> save(@RequestBody VolunteerDto volunteerDto) {
        return ResponseEntity.ok().body(RestResponse.builder()
                .timestamp(LocalDateTime.now())
                .payload(volunteerService.save(volunteerDto))
                .build());
    }

    @Override
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid VolunteerDto volunteerDto) {
        return ResponseEntity.ok().body(RestResponse.builder()
                .timestamp(LocalDateTime.now())
                .payload(volunteerService.update(volunteerDto))
                .build());
    }
}
