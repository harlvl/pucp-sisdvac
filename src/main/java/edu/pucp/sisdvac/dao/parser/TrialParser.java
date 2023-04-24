package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.domain.Trial;

public class TrialParser {
    public static TrialDto toDto(Trial input) {
        TrialDto output = BaseParser.parse(input, TrialDto.class);
        output.setStatus(TrialStatusParser.toDto(input.getStatus())); // TODO check if this is actually necessary
        return output;
    }

    public static Trial fromDto(TrialDto input){
        Trial output = BaseParser.parse(input, Trial.class);
        output.setStatus(TrialStatusParser.fromDto(input.getStatus())); // TODO same
        return output;
    }
}
