package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.TrialStatusDto;
import edu.pucp.sisdvac.domain.TrialStatus;

public class TrialStatusParser {
    public static TrialStatusDto toDto(TrialStatus input) {
        return BaseParser.parse(input, TrialStatusDto.class);
    }

    public static TrialStatus fromDto(TrialStatusDto input){
        return BaseParser.parse(input, TrialStatus.class);
    }
}
