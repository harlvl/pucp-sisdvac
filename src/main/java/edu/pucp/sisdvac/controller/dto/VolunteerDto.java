package edu.pucp.sisdvac.controller.dto;


import edu.pucp.sisdvac.domain.enums.DocumentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerDto {
    private Integer id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private DocumentType documentType;
    @NotNull
    private String documentNumber;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String email;
    @NotNull
    private String contactNumber;
}
