package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.AnimalStudyEvaluationDto;
import edu.pucp.sisdvac.controller.request.AnimalStudyEvaluationRequest;
import edu.pucp.sisdvac.domain.AnimalStudyEvaluation;

import java.math.BigDecimal;
import java.util.Map;

public class AnimalStudyEvaluationParser {
    public static AnimalStudyEvaluationDto toDto(AnimalStudyEvaluation input) {
        AnimalStudyEvaluationDto response = BaseParser.parse(input, AnimalStudyEvaluationDto.class);

        response.setId(input.getId());
        response.setItems(GenericParser.convertItemCollectionToMap(input.getItems()));

        return response;
    }

    public static AnimalStudyEvaluation fromDto(AnimalStudyEvaluationDto input) {
        AnimalStudyEvaluation response = BaseParser.parse(input, AnimalStudyEvaluation.class);

        response.setId(input.getId());
        response.setItems(GenericParser.convertMapToItemCollection(input.getItems()));

        return response;
    }

    public static AnimalStudyEvaluation fromMap(Map<String, BigDecimal> input) {
        AnimalStudyEvaluation response = new AnimalStudyEvaluation();

        response.setItems(GenericParser.convertMapToItemCollection(input));

        return response;
    }
}
