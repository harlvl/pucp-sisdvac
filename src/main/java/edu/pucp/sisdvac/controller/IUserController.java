package edu.pucp.sisdvac.controller;

import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.domain.user.Role;
import org.springframework.http.ResponseEntity;

public interface IUserController {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Integer id);
    ResponseEntity<?> findByDocumentNumber(String key);
    ResponseEntity<?> findByRole(Role role);
    ResponseEntity<?> save(UserDto dto);
    ResponseEntity<?> update(Integer id, UserDto dto);
}
