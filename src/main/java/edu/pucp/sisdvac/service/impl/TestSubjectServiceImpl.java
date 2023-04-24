package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.TestSubjectDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.dao.TestSubjectRepository;
import edu.pucp.sisdvac.dao.parser.BaseParser;
import edu.pucp.sisdvac.dao.parser.TestSubjectParser;
import edu.pucp.sisdvac.domain.TestSubject;
import edu.pucp.sisdvac.service.ITestSubjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestSubjectServiceImpl implements ITestSubjectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestSubjectServiceImpl.class);
    private final TestSubjectRepository repository;

    @Override
    public List<TestSubjectDto> findAll() {
        LOGGER.error("PRUEBA DE LOG LEVEL ERROR");
        List<TestSubject> items = repository.findAll();
        List<TestSubjectDto> response = new ArrayList<>();
        for (TestSubject item :
                items) {
            response.add(TestSubjectParser.toDto(item));
        }
        return response;
    }

    @Override
    public TestSubjectDto findById(Integer id) {
        return TestSubjectParser.toDto(
                repository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Test subject not found"))
        );
    }

    @Override
    public TestSubjectDto save(TestSubjectDto dto) {
        return TestSubjectParser.toDto(
                repository.save(
                        TestSubjectParser.fromDto(dto)
                )
        );
    }

    @Override
    public TestSubjectDto update(TestSubjectDto dto) {
        TestSubject dbItem = repository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException(String.format(
                                "Test subject %d not found", dto.getId()))
                );

        return TestSubjectParser.toDto(
                repository.save(
                        BaseParser.copyProperties(dto, dbItem)
                )
        );
    }
}
