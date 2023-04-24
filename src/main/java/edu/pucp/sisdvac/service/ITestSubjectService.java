package edu.pucp.sisdvac.service;

import edu.pucp.sisdvac.controller.dto.TestSubjectDto;

import java.util.List;
import java.util.Optional;

public interface ITestSubjectService {
    List<TestSubjectDto> findAll();

    TestSubjectDto findById(Integer id);

    TestSubjectDto save(TestSubjectDto testSubjectDto);

    TestSubjectDto update(TestSubjectDto testSubjectDto);
}
