package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.AdvanceDto;
import edu.pucp.sisdvac.controller.dto.AdverseEventDto;
import edu.pucp.sisdvac.domain.Advance;
import edu.pucp.sisdvac.domain.AdverseEvent;

import java.util.ArrayList;
import java.util.Collection;

public class AdvanceParser {

    public static AdvanceDto toDto(Advance input) {
        AdvanceDto response = BaseParser.parse(input, AdvanceDto.class);

        if (input.getAnimalStudy() != null) {
            response.setAnimalStudy(AnimalStudyParser.toDto(input.getAnimalStudy()));
        }

        if (input.getAdverseEvents() != null && !input.getAdverseEvents().isEmpty()) {
            Collection<AdverseEventDto> adverseEventDtos = new ArrayList<>();
            for (AdverseEvent adverseEvent :
                    input.getAdverseEvents()) {
                adverseEventDtos.add(AdverseEventParser.toDto(adverseEvent));
            }
            response.setAdverseEvents(adverseEventDtos);
        }

        return response;
    }

    public static Advance fromDto(AdvanceDto input) {
        Advance response = BaseParser.parse(input, Advance.class);

        if (input.getAnimalStudy() != null) {
            response.setAnimalStudy(AnimalStudyParser.fromDto(input.getAnimalStudy()));
        }

        if (input.getAdverseEvents() != null && !input.getAdverseEvents().isEmpty()) {
            Collection<AdverseEvent> adverseEvents = new ArrayList<>();
            for (AdverseEventDto adverseEventDto :
                    input.getAdverseEvents()) {
                adverseEvents.add(AdverseEventParser.fromDto(adverseEventDto));
            }
            response.setAdverseEvents(adverseEvents);
        }

        return response;
    }
}
