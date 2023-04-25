package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.InfectiousDiseaseDto;
import edu.pucp.sisdvac.domain.InfectiousDisease;

public class InfectiousDiseaseParser {
    public static InfectiousDiseaseDto toDto(InfectiousDisease input) {
        return BaseParser.parse(input, InfectiousDiseaseDto.class);
    }

    public static InfectiousDisease fromDto(InfectiousDiseaseDto input){
        return BaseParser.parse(input, InfectiousDisease.class);
    }
}
