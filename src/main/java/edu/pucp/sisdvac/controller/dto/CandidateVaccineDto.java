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
public class CandidateVaccineDto {
    private Integer id;
    @NotNull
    private String name;
    private String code;
    @NotNull
    private String description;
    private Double efficacy;
    private Double efficiency;
    private Double immunogenicity;
    private Double safety;
    private TrialDto trialDto;
}
