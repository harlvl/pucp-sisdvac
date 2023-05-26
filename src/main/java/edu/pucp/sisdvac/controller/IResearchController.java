package edu.pucp.sisdvac.controller;

import edu.pucp.sisdvac.controller.dto.ResearchDto;
import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.controller.request.AddUsersRequest;
import edu.pucp.sisdvac.domain.user.Role;
import org.springframework.http.ResponseEntity;

public interface IResearchController {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Integer id);
    ResponseEntity<?> findByUser(Integer userId);
    ResponseEntity<?> findByInsNumber(String key);
    ResponseEntity<?> findUsersByRole(Integer id, Role key);
    ResponseEntity<?> save(ResearchDto dto);
    ResponseEntity<?> update(Integer id, ResearchDto dto);
    ResponseEntity<?> addUser(Integer id, UserDto dto);
    ResponseEntity<?> addUsers(Integer id, AddUsersRequest request);
    ResponseEntity<?> findAnimalStudiesByUserAndTrial(String documentNumber, Integer trialId);
    ResponseEntity<?> findAnimalStudiesByUser(String documentNumber);
}
