package edu.pucp.sisdvac.dao;

import edu.pucp.sisdvac.domain.TestVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<TestVolunteer, Integer> {
    List<TestVolunteer> findAll();
}
