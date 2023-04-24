package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.VolunteerDto;
import edu.pucp.sisdvac.domain.TestVolunteer;

public class VolunteerParser {

    public static VolunteerDto toDto(TestVolunteer testVolunteer){
        return BaseParser.parse(testVolunteer, VolunteerDto.class);
    }

    public static TestVolunteer fromDto(VolunteerDto volunteerDto){
        return BaseParser.parse(volunteerDto, TestVolunteer.class);
    }
}
