package edu.pucp.sisdvac.controller;

import org.springframework.http.ResponseEntity;

public interface IVolunteerController {
    ResponseEntity<?> getVolunteers();
}
