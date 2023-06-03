package edu.pucp.sisdvac.controller;

import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.domain.user.Role;
import edu.pucp.sisdvac.security.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface IUserController {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Integer id);
    ResponseEntity<?> findByDocumentNumber(String key);
    ResponseEntity<?> findByName(String key); // TODO handle first and last name at the same time
    ResponseEntity<?> findByRole(Role role);
    ResponseEntity<?> save(RegisterRequest dto);
    ResponseEntity<?> update(Integer id, UserDto dto);
}
