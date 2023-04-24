package edu.pucp.sisdvac.controller.dto;


import edu.pucp.sisdvac.domain.enums.DocumentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerDto {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
}
