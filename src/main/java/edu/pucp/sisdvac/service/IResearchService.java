package edu.pucp.sisdvac.service;

import edu.pucp.sisdvac.controller.dto.ResearchDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.controller.request.AddUsersRequest;
import edu.pucp.sisdvac.domain.user.Role;

import java.util.List;

public interface IResearchService {
    List<ResearchDto> findAll();
    ResearchDto findById(Integer id);
    ResearchDto findByInsNumber(String key);
    List<ResearchDto> findByUserId(Integer id);
    List<ResearchDto> findByUserDocumentNumber(String key);
    List<?> findUsersByRole(Integer id, Role role);
    ResearchDto save(ResearchDto dto);
    ResearchDto update(Integer id, ResearchDto dto);
    ResearchDto addUser(Integer id, UserDto dto);
    ResearchDto addUsers(Integer id, AddUsersRequest dto);

    // TRIAL LEVEL METHODS
    Object findAnimalStudiesByUserAndTrial(String documentNumber, Integer trialId);
    Object findAnimalStudiesByUser(String documentNumber);
    Object findClinicalStudiesByUser(String documentNumber);
    List<TrialDto> findTrialsByUserDocumentNumber(String key);
}
