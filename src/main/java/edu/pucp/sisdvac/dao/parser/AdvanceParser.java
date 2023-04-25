package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.domain.Advance;

public class AdvanceParser {
    public static TrialDto.AdvanceItem toAdvanceItem(Advance input) {
        return BaseParser.parse(input, TrialDto.AdvanceItem.class);
    }

    public static Advance toAdvance(TrialDto.AdvanceItem input) {
        return BaseParser.parse(input, Advance.class);
    }
}
