package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.FormulationDto;
import edu.pucp.sisdvac.controller.dto.FormulationEvaluationDto;
import edu.pucp.sisdvac.controller.dto.FormulationItemDto;
import edu.pucp.sisdvac.domain.EvaluationItem;
import edu.pucp.sisdvac.domain.Formulation;
import edu.pucp.sisdvac.domain.FormulationItem;
import edu.pucp.sisdvac.domain.enums.FormulationStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormulationParser {
    public static FormulationDto toDto(Formulation input) {
        FormulationDto output = BaseParser.parse(input, FormulationDto.class);
        List<FormulationItemDto> itemDtos = new ArrayList<>();

        for (FormulationItem item:
                input.getItems()) {
            FormulationItemDto formulationItemDto = FormulationItemDto.builder()
                    .type(item.getType())
                    .detail(item.getDetail())
                    .build();

            if (item.getId() != null) {
                // copy id in case the object already exists
                formulationItemDto.setId(item.getId());
            }

            if (input.getEvaluation() != null) {
                output.setEvaluation(
                        FormulationEvaluationDto.builder()
                                .id(input.getEvaluation().getId())
                                .items(getItems(input.getEvaluation().getItems()))
                                .build()
                );
            }

            itemDtos.add(formulationItemDto);
        }

        output.setItems(itemDtos);

        return output;
    }

    public static Map<String, BigDecimal> getItems(Collection<EvaluationItem> items) {
        HashMap<String, BigDecimal> response = new HashMap<>();

        for (EvaluationItem item :
                items) {
            response.put(String.valueOf(item.getType()), item.getDetail());
        }

        return response;
    }

    public static Formulation fromDto(FormulationDto input) {
        Formulation output = BaseParser.parse(input, Formulation.class);
        List<FormulationItem> items = new ArrayList<>();

        for (FormulationItemDto item :
                input.getItems()) {
            FormulationItem formulationItem = FormulationItem.builder()
                    .type(item.getType())
                    .detail(item.getDetail())
                    .build();

            if (item.getId() != null) {
                formulationItem.setId(item.getId());
            }

            items.add(formulationItem);
        }

        output.setItems(items);

        return output;
    }

    public static Collection<Formulation> updateFormulations(Collection<Formulation> formulations, FormulationDto dto) {
        Collection<Formulation> response = new ArrayList<>();

        // set all previous formulations to inactive
        Integer lastFormulation = 0;
        for (Formulation item :
                formulations) {
            Integer currentFormulation = item.getOrder();
            response.add(Formulation.builder()
                    .id(item.getId())
                    .order(item.getOrder())
                    .status(FormulationStatus.INACTIVE)
                    .items(item.getItems())
                    .build()
            );

            if (currentFormulation >= lastFormulation) {
                lastFormulation = currentFormulation;
            }
        }

        // add the new formulation and set it to active
        Formulation newElement = FormulationParser.fromDto(dto);
        newElement.setStatus(FormulationStatus.ACTIVE);
        newElement.setOrder(lastFormulation + 1);

        response.add(newElement);

        return response;
    }
}
