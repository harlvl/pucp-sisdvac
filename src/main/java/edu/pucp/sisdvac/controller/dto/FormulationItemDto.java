package edu.pucp.sisdvac.controller.dto;

import edu.pucp.sisdvac.domain.enums.FormulationItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormulationItemDto {
    private Integer id;

    @NotNull
    private FormulationItemType type;

    @NotNull
    private String detail;
}
