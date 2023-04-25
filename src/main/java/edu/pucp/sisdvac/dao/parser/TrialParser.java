package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.domain.Advance;
import edu.pucp.sisdvac.domain.Trial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrialParser {
    public static TrialDto toDto(Trial input) {
        TrialDto output = BaseParser.parse(input, TrialDto.class);
        output.setStatus(TrialStatusParser.toDto(input.getStatus())); // TODO check if this is actually necessary
        Collection<Advance> advances = input.getAdvances();

        List<TrialDto.AdvanceItem> advanceItems = new ArrayList<>();
        for (Advance item :
                advances) {
            advanceItems.add(
                    AdvanceParser.toAdvanceItem(item)
            );
        }
        output.setAdvanceItems(advanceItems);

        return output;
    }

    public static Trial fromDto(TrialDto input){
        Trial output = BaseParser.parse(input, Trial.class);
        output.setStatus(TrialStatusParser.fromDto(input.getStatus())); // TODO same

        // build advances list
        List<TrialDto.AdvanceItem> advanceItems = input.getAdvanceItems();
        List<Advance> advances = new ArrayList<>();
        for (TrialDto.AdvanceItem item :
                advanceItems) {
            advances.add(
                    AdvanceParser.toAdvance(item)
            );
        }
        output.setAdvances(advances);

        return output;
    }
}
