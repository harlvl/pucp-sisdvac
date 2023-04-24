package edu.pucp.sisdvac.controller.impl;

import edu.pucp.sisdvac.controller.IVolunteerController;
import edu.pucp.sisdvac.controller.dto.VolunteerDto;
import edu.pucp.sisdvac.controller.response.RestResponse;
import edu.pucp.sisdvac.service.IVolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/volunteer")
@RequiredArgsConstructor
public class VolunteerControllerImpl implements IVolunteerController {
    private final IVolunteerService volunteerService;
    @Override
    @GetMapping
    public ResponseEntity<?> getVolunteers() {
        List<VolunteerDto> volunteerDtos = volunteerService.getVolunteers();
        return ResponseEntity.ok().body(RestResponse.builder().timestamp(LocalDateTime.now()).payload(volunteerDtos).build());
    }
}
