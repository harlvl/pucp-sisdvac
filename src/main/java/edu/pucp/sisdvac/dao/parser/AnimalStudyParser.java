package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.AnimalStudyDto;
import edu.pucp.sisdvac.domain.AnimalStudy;

public class AnimalStudyParser {
    public static AnimalStudyDto toDto(AnimalStudy input) {
        AnimalStudyDto response = BaseParser.parse(input, AnimalStudyDto.class);

        if (input.getEvaluation() != null) {
            response.setEvaluation(AnimalStudyEvaluationParser.toDto(input.getEvaluation()));
        }

        return response;
    }

    public static AnimalStudy fromDto(AnimalStudyDto input) {
        AnimalStudy response = BaseParser.parse(input, AnimalStudy.class);

        if (input.getEvaluation() != null) {
            response.setEvaluation(AnimalStudyEvaluationParser.fromDto(input.getEvaluation()));
        }

        return response;
    }
}
