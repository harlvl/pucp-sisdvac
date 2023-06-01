package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.AnimalStudyDto;
import edu.pucp.sisdvac.controller.dto.GenericStudyDto;
import edu.pucp.sisdvac.domain.AnimalStudy;
import edu.pucp.sisdvac.domain.GenericStudy;

public class GenericStudyParser {
    public static AnimalStudyDto toDto(AnimalStudy input) {
        AnimalStudyDto response = BaseParser.parse(input, AnimalStudyDto.class);

        if (input.getEvaluation() != null) {
            response.setEvaluation(GenericStudyEvaluationParser.toDto(input.getEvaluation()));
        }

        return response;
    }

    public static GenericStudyDto toDto(GenericStudy input) {
        GenericStudyDto response = BaseParser.parse(input, GenericStudyDto.class);

        if (input.getEvaluation() != null) {
            response.setEvaluation(GenericStudyEvaluationParser.toDto(input.getEvaluation()));
        }

        return response;
    }

    public static AnimalStudy fromDto(AnimalStudyDto input) {
        AnimalStudy response = BaseParser.parse(input, AnimalStudy.class);

        if (input.getEvaluation() != null) {
            response.setEvaluation(GenericStudyEvaluationParser.fromDto(input.getEvaluation()));
        }

        return response;
    }

    public static GenericStudy fromDto(GenericStudyDto input) {
        GenericStudy response = BaseParser.parse(input, GenericStudy.class);

        if (input.getEvaluation() != null) {
            response.setEvaluation(GenericStudyEvaluationParser.fromDto(input.getEvaluation()));
        }

        return response;
    }
}
