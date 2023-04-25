package edu.pucp.sisdvac.dao;

import edu.pucp.sisdvac.domain.TestSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestSubjectRepository extends JpaRepository<TestSubject, Integer> {
    List<TestSubject> findAll();
    Optional<TestSubject> findById(Integer id);
    Optional<TestSubject> findByCodeName(String key);

}
