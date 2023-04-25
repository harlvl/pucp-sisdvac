package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.AdverseEventDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.domain.Advance;
import edu.pucp.sisdvac.domain.AdverseEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdvanceParser {
    public static TrialDto.AdvanceItem toAdvanceItem(Advance input) {
        TrialDto.AdvanceItem output = BaseParser.parse(input, TrialDto.AdvanceItem.class);
        if (input.getAdverseEvents() != null && !input.getAdverseEvents().isEmpty()) {
            List<AdverseEventDto> adverseEventDtos = new ArrayList<>();
            for (AdverseEvent adverseEvent :
                    input.getAdverseEvents()) {
                adverseEventDtos.add(AdverseEventParser.toDto(adverseEvent));
            }
            output.setAdverseEvents(adverseEventDtos);
        }

        return output;
    }

    public static Advance toAdvance(TrialDto.AdvanceItem input) {
        Advance output = BaseParser.parse(input, Advance.class);
        if (input.getAdverseEvents() != null && !input.getAdverseEvents().isEmpty()) {
            Collection<AdverseEvent> adverseEvents = new ArrayList<>();
            for (AdverseEventDto adverseEventDto :
                    input.getAdverseEvents()) {
                adverseEvents.add(AdverseEventParser.fromDto(adverseEventDto));
            }
            output.setAdverseEvents(adverseEvents);
        }

        return output;
    }
}
