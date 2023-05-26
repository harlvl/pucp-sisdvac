package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.AnimalStudyDto;
import edu.pucp.sisdvac.domain.AnimalStudy;

public class AnimalStudyParser {
    public static AnimalStudyDto toDto(AnimalStudy input) {
        return BaseParser.parse(input, AnimalStudyDto.class);
    }

    public static AnimalStudy fromDto(AnimalStudyDto input) {
        return BaseParser.parse(input, AnimalStudy.class);
    }
}
