package edu.pucp.sisdvac.controller.impl;

import edu.pucp.sisdvac.controller.IResearchController;
import edu.pucp.sisdvac.controller.dto.ResearchDto;
import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.controller.response.PayloadObjectBuilder;
import edu.pucp.sisdvac.controller.response.RestResponse;
import edu.pucp.sisdvac.domain.user.Role;
import edu.pucp.sisdvac.service.IResearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/research")
@RequiredArgsConstructor
public class ResearchController implements IResearchController {
    private final IResearchService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<?> dtos = service.findAll();
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
    @GetMapping("/user_id/{id}")
    public ResponseEntity<?> findByUser(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(service.findByUserId(id))
                        .hits(1)
                        .build()
        );
    }

    @Override
    @GetMapping("/ins_number/{insNumber}")
    public ResponseEntity<?> findByInsNumber(@PathVariable(name = "insNumber") final String key) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(service.findByInsNumber(key))
                        .hits(1)
                        .build()
        );
    }

    @Override
    @GetMapping("/{id}/users/role/{key}")
    public ResponseEntity<?> findUsersByRole(@PathVariable(name = "id") final Integer id,
                                             @PathVariable(name = "key") final Role key) {
        List<?> output = service.findUsersByRole(id, key);
        return ResponseEntity.ok().body(
                PayloadObjectBuilder.buildPayloadObject(output, output.size())
        );
    }

    @Override
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ResearchDto dto) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(service.save(dto))
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @Override
    @PatchMapping("/id")
    public ResponseEntity<?> update(Integer id, ResearchDto dto) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(service.update(id, dto))
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @Override
    @PostMapping("/id/{id}/add_user")
    public ResponseEntity<?> addUser(@PathVariable(name = "id") final Integer id, @RequestBody UserDto dto) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(service.addUser(id, dto))
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
