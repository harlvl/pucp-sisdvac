package edu.pucp.sisdvac.security.auth;

import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.controller.response.RestResponse;
import edu.pucp.sisdvac.security.config.LogoutService;
import edu.pucp.sisdvac.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;
    private final IUserService userService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/user")
    public ResponseEntity<?> findAll() {
        List<?> output = userService.findAll();
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(output)
                        .hits(output.size())
                        .build()
        );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> findUserById(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(userService.findById(id))
                        .hits(1)
                        .build()
        );
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") final Integer id, @RequestBody UserDto request) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(userService.update(id, request))
                        .build()
        );
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable(name = "email") final String email) {
        return ResponseEntity.ok().body(
                RestResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .payload(userService.findByEmail(email))
                        .hits(1)
                        .build()
        );
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        logoutService.logout(request, response, null);
    }
}
