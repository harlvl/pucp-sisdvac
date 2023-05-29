package edu.pucp.sisdvac.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
