package edu.pucp.sisdvac.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfectiousDiseaseDto {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    private String source; // should be a website

    private String realm;
    private String variant;
}
