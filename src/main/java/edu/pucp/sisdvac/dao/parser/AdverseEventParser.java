package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.AdverseEventDto;
import edu.pucp.sisdvac.domain.AdverseEvent;

public class AdverseEventParser {
    public static AdverseEventDto toDto(AdverseEvent input) {
        return BaseParser.parse(input, AdverseEventDto.class);
    }

    public static AdverseEvent fromDto(AdverseEventDto input){
        return BaseParser.parse(input, AdverseEvent.class);
    }
}
