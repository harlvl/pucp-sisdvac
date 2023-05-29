package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.FormulationEvaluationDto;
import edu.pucp.sisdvac.domain.EvaluationItem;
import edu.pucp.sisdvac.domain.FormulationEvaluation;
import edu.pucp.sisdvac.domain.enums.EvaluationFormulaEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
