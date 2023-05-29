package edu.pucp.sisdvac.security.auth;

import edu.pucp.sisdvac.domain.enums.DocumentType;
import edu.pucp.sisdvac.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Role role;
    @NotNull
    private DocumentType documentType;
    @NotNull
    private String documentNumber;
}
