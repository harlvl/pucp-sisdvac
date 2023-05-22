package edu.pucp.sisdvac.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormulationEvaluationDto {
    private Integer id;
    private Map<String, Object> items;
}
