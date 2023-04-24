package edu.pucp.sisdvac.controller;

import edu.pucp.sisdvac.controller.dto.VolunteerDto;
import org.springframework.http.ResponseEntity;

public interface IVolunteerController {
    ResponseEntity<?> getVolunteers();
    ResponseEntity<?> saveVolunteer(VolunteerDto volunteerDto);
    ResponseEntity<?> updateVolunteer(VolunteerDto volunteerDto);
}
