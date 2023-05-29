package edu.pucp.sisdvac.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalStudyEvaluationRequest {
    // for efficacy and efficiency
    @NotNull
    private Double attackRateUnvaccinatedGroup;
    @NotNull
    private Double attackRateVaccinatedGroup;

    // for immunogenicity
    @NotNull
    private Integer animalsWithDetectableImmuneResponse;

    // for safety index
    @NotNull
    private Double lethalDose;
    @NotNull
    private Double effectiveDose;

    // for incidence rate
    @NotNull
    private Integer adverseEventsVaccinatedGroup;
    @NotNull
    private Integer totalVaccinatedAnimals;
}
