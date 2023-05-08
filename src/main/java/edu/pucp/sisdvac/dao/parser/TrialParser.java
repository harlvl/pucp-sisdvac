package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.FormulationDto;
import edu.pucp.sisdvac.controller.dto.TppDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.domain.Advance;
import edu.pucp.sisdvac.domain.Formulation;
import edu.pucp.sisdvac.domain.TargetProductProfile;
import edu.pucp.sisdvac.domain.Trial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class TrialParser {
    public static TrialDto toDto(Trial input) {
        TrialDto output = BaseParser.parse(input, TrialDto.class);
        output.setStatus(TrialStatusParser.toDto(input.getStatus())); // TODO check if this is actually necessary

        if (input.getAdvances() != null && input.getAdvances().size() > 0) {
            Collection<Advance> advances = input.getAdvances();
            List<TrialDto.AdvanceItem> advanceItems = new ArrayList<>();
            for (Advance item :
                    advances) {
                advanceItems.add(
                        AdvanceParser.toAdvanceItem(item)
                );
            }
            output.setAdvanceItems(advanceItems);
        }

        if (input.getFormulations() != null && !input.getFormulations().isEmpty()) {
            List<FormulationDto> formulationDtos = new ArrayList<>();
            for (Formulation item :
                    input.getFormulations()) {
                formulationDtos.add(FormulationParser.toDto(item));
            }

            output.setFormulations(formulationDtos);
        }

        output.setTpp(TppParser.toDto(input.getTargetProductProfile()));

        return output;
    }

    public static Trial fromDto(TrialDto input){
        Trial output = BaseParser.parse(input, Trial.class);
        output.setStatus(TrialStatusParser.fromDto(input.getStatus())); // TODO same

        // build advances list
        if (input.getAdvanceItems() != null && input.getAdvanceItems().size() > 0) {
            List<TrialDto.AdvanceItem> advanceItems = input.getAdvanceItems();
            List<Advance> advances = new ArrayList<>();
            for (TrialDto.AdvanceItem item :
                    advanceItems) {
                advances.add(
                        AdvanceParser.toAdvance(item)
                );
            }

            output.setAdvances(advances);
        }

        if (input.getFormulations() != null && !input.getFormulations().isEmpty()) {
            List<FormulationDto> formulationDtos = input.getFormulations();
            List<Formulation> formulations = new ArrayList<>();
            for (FormulationDto item :
                    formulationDtos) {
                formulations.add(FormulationParser.fromDto(item));
            }

            output.setFormulations(formulations);
        }

        output.setTargetProductProfile(TppParser.fromDto(input.getTpp()));

        // add created at date
        output.setCreatedAt(new Date());
        output.setLastUpdatedAt(new Date());

        return output;
    }

    public static TargetProductProfile updateTpp(TppDto input) {
        return TppParser.fromDto(input);
    }

    public static Formulation updateFormulation(FormulationDto input) {
        return FormulationParser.fromDto(input);
    }
}
