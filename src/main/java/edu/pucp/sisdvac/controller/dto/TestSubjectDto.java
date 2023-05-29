package edu.pucp.sisdvac.controller.dto;

import edu.pucp.sisdvac.domain.enums.Status;
import edu.pucp.sisdvac.domain.enums.SubjectType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestSubjectDto {
    private Integer id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private SubjectType subjectType;
    @NotNull
    private String codeName;
    @Enumerated(EnumType.STRING)
    private Status status;
}
