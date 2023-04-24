package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.TestSubjectDto;
import edu.pucp.sisdvac.controller.dto.VolunteerDto;
import edu.pucp.sisdvac.domain.TestSubject;
import edu.pucp.sisdvac.domain.TestVolunteer;
import edu.pucp.sisdvac.domain.enums.Status;
import edu.pucp.sisdvac.domain.enums.SubjectType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class TestSubjectParser {
    public static TestSubjectDto toDto(TestSubject testSubject) {
        return BaseParser.parse(testSubject, TestSubjectDto.class);
    }

    public static TestSubject fromDto(TestSubjectDto testSubjectDto){
        return BaseParser.parse(testSubjectDto, TestSubject.class);
    }
}
