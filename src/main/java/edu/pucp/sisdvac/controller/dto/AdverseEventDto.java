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
public class AdverseEventDto {
    private Integer id;
    @NotNull
    private String patientCID;
    @NotNull
    private String description;
    @NotNull
    private Integer age;
    @NotNull
    private char sex; // F or M
    private Double weight;
    private Double height;
}
