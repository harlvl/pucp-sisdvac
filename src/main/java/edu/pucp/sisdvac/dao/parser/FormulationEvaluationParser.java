package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.FormulationEvaluationDto;
import edu.pucp.sisdvac.domain.FormulationEvaluation;

public class FormulationEvaluationParser {
    public static FormulationEvaluationDto toDto(FormulationEvaluation input) {
        FormulationEvaluationDto response = BaseParser.parse(input, FormulationEvaluationDto.class);

        response.setId(input.getId());
        response.setItems(GenericParser.convertItemCollectionToMap(input.getItems()));

        return response;
    }

    public static FormulationEvaluation fromDto(FormulationEvaluationDto input) {
        FormulationEvaluation response = BaseParser.parse(input, FormulationEvaluation.class);

        response.setId(input.getId());
        response.setItems(GenericParser.convertMapToItemCollection(input.getItems()));

        return response;
    }
}
