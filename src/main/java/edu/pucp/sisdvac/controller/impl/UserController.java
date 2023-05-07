package edu.pucp.sisdvac.controller.impl;

import edu.pucp.sisdvac.controller.IUserController;
import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.controller.response.PayloadObjectBuilder;
import edu.pucp.sisdvac.domain.user.Role;
import edu.pucp.sisdvac.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController implements IUserController {
    private final IUserService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<?> dtos = service.findAll();
        return ResponseEntity.ok().body(
                PayloadObjectBuilder.buildPayloadObject(
                        dtos, dtos.size()
                )
        );
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok().body(
                PayloadObjectBuilder.buildPayloadObject(
                        service.findById(id), 1
                )
        );
    }

    @Override
    @GetMapping("/document_number/{key}")
    public ResponseEntity<?> findByDocumentNumber(@PathVariable(name = "key") final String key) {
        return ResponseEntity.ok().body(
                PayloadObjectBuilder.buildPayloadObject(
                        service.findByDocumentNumber(key), 1
                )
        );
    }

    @Override
    @GetMapping("/role/{key}")
    public ResponseEntity<?> findByRole(@PathVariable(name = "key") final Role key) { // TODO clean input for key
        List<?> dtos = service.findByRole(key);
        return ResponseEntity.ok().body(
                PayloadObjectBuilder.buildPayloadObject(
                        dtos, dtos.size()
                )
        );
    }

    @Override
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UserDto dto) {
        return ResponseEntity.ok().body(
                PayloadObjectBuilder.buildPayloadObject(
                        service.save(dto)
                )
        );
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable (name = "id") Integer id, @Valid @RequestBody UserDto dto) {
        return ResponseEntity.ok().body(
                PayloadObjectBuilder.buildPayloadObject(
                        service.update(id, dto)
                )
        );
    }
}
