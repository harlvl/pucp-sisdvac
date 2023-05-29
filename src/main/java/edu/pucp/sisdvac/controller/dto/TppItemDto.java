package edu.pucp.sisdvac.controller.dto;

import edu.pucp.sisdvac.domain.enums.TppItemType;
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
public class TppItemDto {
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TppItemType type;

    @NotNull
    private String detail;
}
