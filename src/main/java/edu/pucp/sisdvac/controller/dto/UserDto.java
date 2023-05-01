package edu.pucp.sisdvac.controller.dto;

import edu.pucp.sisdvac.domain.enums.DocumentType;
import edu.pucp.sisdvac.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;

    @NotNull
    private String documentNumber;

    @NotNull
    private DocumentType documentType;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private Role role;
}
