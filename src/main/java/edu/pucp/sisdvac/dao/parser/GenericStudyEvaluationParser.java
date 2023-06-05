package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.AnimalStudyEvaluationDto;
import edu.pucp.sisdvac.controller.dto.GenericStudyEvaluationDto;
import edu.pucp.sisdvac.domain.AnimalStudyEvaluation;
import edu.pucp.sisdvac.domain.GenericStudyEvaluation;

import java.math.BigDecimal;
import java.util.Map;

public class GenericStudyEvaluationParser {
    public static AnimalStudyEvaluationDto toDto(AnimalStudyEvaluation input) {
        AnimalStudyEvaluationDto response = BaseParser.parse(input, AnimalStudyEvaluationDto.class);

        response.setId(input.getId());
        response.setItems(GenericParser.convertItemCollectionToMap(input.getItems()));

        return response;
    }

    public static GenericStudyEvaluationDto toDto(GenericStudyEvaluation input) {
        GenericStudyEvaluationDto response = BaseParser.parse(input, GenericStudyEvaluationDto.class);

        response.setId(input.getId());
        response.setItems(GenericParser.convertGenericItemCollectionToMap(input.getItems()));

        return response;
    }

    public static AnimalStudyEvaluation fromDto(AnimalStudyEvaluationDto input) {
        AnimalStudyEvaluation response = BaseParser.parse(input, AnimalStudyEvaluation.class);

        response.setId(input.getId());
        response.setItems(GenericParser.convertMapToItemCollection(input.getItems()));

        return response;
    }

    public static GenericStudyEvaluation fromDto(GenericStudyEvaluationDto input) {
        GenericStudyEvaluation response = BaseParser.parse(input, GenericStudyEvaluation.class);

        response.setId(input.getId());
        response.setItems(GenericParser.convertMapToGenericItemCollection(input.getItems()));

        return response;
    }

    public static AnimalStudyEvaluation fromMap(Map<String, BigDecimal> input) {
        AnimalStudyEvaluation response = new AnimalStudyEvaluation();

        response.setItems(GenericParser.convertMapToItemCollection(input));

        return response;
    }
}
