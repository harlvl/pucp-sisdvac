package edu.pucp.sisdvac.controller.dto;

import edu.pucp.sisdvac.domain.enums.FormulationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormulationDto {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private FormulationStatus status;
    private Integer order;
    private Collection<FormulationItemDto> items;
}
