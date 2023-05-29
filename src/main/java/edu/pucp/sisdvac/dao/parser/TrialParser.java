package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.*;
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

        if (input.getAdvances() != null && !input.getAdvances().isEmpty()) {
            Collection<Advance> advances = input.getAdvances();
            Collection<AdvanceDto> advanceDtos = new ArrayList<>();
            for (Advance item :
                    advances) {
                advanceDtos.add(AdvanceParser.toDto(item));
            }

            output.setAdvanceDtos(advanceDtos);
        }

        if (input.getFormulations() != null && !input.getFormulations().isEmpty()) {
            List<FormulationDto> formulationDtos = new ArrayList<>();
            for (Formulation item :
                    input.getFormulations()) {
                FormulationDto dto = FormulationParser.toDto(item);
                if (item.getEvaluation() != null) {
                    dto.setEvaluation(
                            FormulationEvaluationDto.builder()
                                    .id(item.getEvaluation().getId())
                                    .items(FormulationParser.getItems(item.getEvaluation().getItems()))
                                    .build()
                    );
                }

                formulationDtos.add(dto);
            }

            output.setFormulations(formulationDtos);
        }

        if (input.getTargetProductProfile() != null) {
            output.setTpp(TppParser.toDto(input.getTargetProductProfile()));
        }

        return output;
    }

    public static Trial fromDto(TrialDto input){
        Trial output = BaseParser.parse(input, Trial.class);
        output.setStatus(TrialStatusParser.fromDto(input.getStatus())); // TODO same

        // build advances list
        if (input.getAdvanceDtos() != null && !input.getAdvanceDtos().isEmpty()) {
            Collection<AdvanceDto> advanceDtos = input.getAdvanceDtos();
            Collection<Advance> advances = new ArrayList<>();
            for (AdvanceDto item :
                    advanceDtos) {
                advances.add(AdvanceParser.fromDto(item));
            }

            output.setAdvances(advances);
        }
//        if (input.getAdvances() != null && input.getAdvances().size() > 0) {
//            List<TrialDto.AdvanceDto> advanceItems = input.getAdvances();
//            List<Advance> advances = new ArrayList<>();
//            for (TrialDto.AdvanceDto item :
//                    advanceItems) {
//                advances.add(
//                        AdvanceParser.fromDto(item)
//                );
//            }
//
//            output.setAdvances(advances);
//        }

        if (input.getFormulations() != null && !input.getFormulations().isEmpty()) {
            List<FormulationDto> formulationDtos = input.getFormulations();
            List<Formulation> formulations = new ArrayList<>();
            for (FormulationDto item :
                    formulationDtos) {
                formulations.add(FormulationParser.fromDto(item));
            }

            output.setFormulations(formulations);
        }

        if (input.getTpp() != null) {
            output.setTargetProductProfile(TppParser.fromDto(input.getTpp()));
        }


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
