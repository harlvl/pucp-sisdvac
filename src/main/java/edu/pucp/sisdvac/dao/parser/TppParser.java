package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.TppDto;
import edu.pucp.sisdvac.controller.dto.TppItemDto;
import edu.pucp.sisdvac.domain.TargetProductProfile;
import edu.pucp.sisdvac.domain.TppItem;

import java.util.ArrayList;
import java.util.List;

public class TppParser {
    public static TppDto toDto(TargetProductProfile input) {
        TppDto output = BaseParser.parse(input, TppDto.class);
        List<TppItemDto> itemDtos = new ArrayList<>();

        for (TppItem item:
             input.getItems()) {
            TppItemDto tppItemDto = TppItemDto.builder()
                    .type(item.getType())
                    .detail(item.getDetail())
                    .build();

            if (item.getId() != null) {
                // copy id in case the object already exists
                tppItemDto.setId(item.getId());
            }

            itemDtos.add(tppItemDto);
        }

        output.setItems(itemDtos);

        return output;
    }

    public static TargetProductProfile fromDto(TppDto input) {
        TargetProductProfile output = BaseParser.parse(input, TargetProductProfile.class);
        List<TppItem> tppItems = new ArrayList<>();

        for (TppItemDto item :
                input.getItems()) {
            TppItem tppItem = TppItem.builder()
                    .type(item.getType())
                    .detail(item.getDetail())
                    .build();

            if (item.getId() != null) {
                tppItem.setId(item.getId());
            }

            tppItems.add(tppItem);
        }

        output.setItems(tppItems);

        return output;
    }
}
