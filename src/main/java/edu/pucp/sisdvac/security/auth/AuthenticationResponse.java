package edu.pucp.sisdvac.security.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.pucp.sisdvac.domain.enums.DocumentType;
import edu.pucp.sisdvac.domain.user.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
