package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.FormulationDto;
import edu.pucp.sisdvac.controller.dto.FormulationItemDto;
import edu.pucp.sisdvac.domain.Formulation;
import edu.pucp.sisdvac.domain.FormulationItem;

import java.util.ArrayList;
import java.util.List;

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

            itemDtos.add(formulationItemDto);
        }

        output.setItems(itemDtos);

        return output;
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
}
