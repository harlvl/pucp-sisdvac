package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.TestSubjectDto;
import edu.pucp.sisdvac.domain.TestSubject;

public class TestSubjectParser {
    public static TestSubjectDto toDto(TestSubject testSubject) {
        return BaseParser.parse(testSubject, TestSubjectDto.class);
    }

    public static TestSubject fromDto(TestSubjectDto testSubjectDto){
        return BaseParser.parse(testSubjectDto, TestSubject.class);
    }
}
