package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.AnimalStudyDto;
import edu.pucp.sisdvac.controller.dto.ResearchDto;
import edu.pucp.sisdvac.controller.dto.TrialDto;
import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.controller.exception.UserAlreadyAddedException;
import edu.pucp.sisdvac.controller.request.AddUsersRequest;
import edu.pucp.sisdvac.dao.ResearchRepository;
import edu.pucp.sisdvac.dao.TrialRepository;
import edu.pucp.sisdvac.dao.UserRepository;
import edu.pucp.sisdvac.dao.parser.*;
import edu.pucp.sisdvac.domain.Advance;
import edu.pucp.sisdvac.domain.Research;
import edu.pucp.sisdvac.domain.Trial;
import edu.pucp.sisdvac.domain.enums.Stage;
import edu.pucp.sisdvac.domain.user.Role;
import edu.pucp.sisdvac.domain.user.User;
import edu.pucp.sisdvac.service.IResearchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResearchServiceImpl implements IResearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResearchServiceImpl.class);
    private final ResearchRepository repository;
    private final UserRepository userRepository;
    private final TrialRepository trialRepository;
    @Override
    public List<ResearchDto> findAll() {
        List<Research> dbItems = repository.findAll();
        List<ResearchDto> response = new ArrayList<>();
        for (Research dbItem :
                dbItems) {
            response.add(ResearchParser.toDto(dbItem));
        }
        return response;
    }

    @Override
    public ResearchDto findById(Integer id) {
        return ResearchParser.toDto(
                repository.findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "Research [%d] not found", id
                        )))
        );
    }

    @Override
    public ResearchDto findByInsNumber(String key) {
        return ResearchParser.toDto(
                repository.findByInsNumber(key)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "Research with INS number [%s] not found", key
                        )))
        );
    }

    @Override
    public List<ResearchDto> findByUserId(Integer id) {
        List<Research> dbItems = repository.findAll();
        List<ResearchDto> output = new ArrayList<>();
        for (Research research :
                dbItems) {
            Collection<User> users = research.getUsers();
            for (User user :
                    users) {
                if (Objects.equals(user.getId(), id)) {
                    output.add(ResearchParser.toDto(research));
                }
            }
        }
        return output;
    }

    @Override
    public List<ResearchDto> findByUserDocumentNumber(String key) {
        List<Research> dbItems = this.findByUserDocumentNumberInternal(key);
        List<ResearchDto> output = new ArrayList<>();
        for (Research research :
                dbItems) {
            output.add(ResearchParser.toDto(research));
        }
        return output;
    }

    private List<Research> findByUserDocumentNumberInternal(String key) {
        List<Research> dbItems = repository.findAll();
        List<Research> output = new ArrayList<>();
        for (Research research :
                dbItems) {
            Collection<User> users = research.getUsers();
            for (User user :
                    users) {
                if (Objects.equals(user.getDocumentNumber(), key)) {
                    output.add(research);
                }
            }
        }

        return output;
    }

    @Override
    public List<?> findUsersByRole(Integer id, Role role) {
        List<UserDto> output = new ArrayList<>();

        Research dbItem = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Research [%d] not found", id
                )));
        Collection<User> dbUsers = dbItem.getUsers();

        for (User item :
                dbUsers) {
            if (item.getRole() == role) {
                output.add(
                        UserParser.toDto(item)
                );
            }
        }

        return output;
    }


    @Override
    public ResearchDto save(ResearchDto dto) {
        LOGGER.info("Creating new research...");
        return ResearchParser.toDto(
                repository.save(
                        ResearchParser.fromDto(dto)
                )
        );
    }

    @Override
    public ResearchDto update(Integer id, ResearchDto dto) {
        LOGGER.info(String.format(
                "Updating existing research [%d]...", id)
        );
        Research dbItem = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Research [%d] not found.", id)
                ));

        return ResearchParser.toDto(
                repository.save(
                        BaseParser.copyProperties(dto, dbItem)
                )
        );
    }

    @Override
    public ResearchDto addUser(Integer id, UserDto dto) {
        LOGGER.info(String.format(
                "Adding user [%d] to research [%d]...", dto.getId(), id)
        );
        Research dbItem = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Research [%d] not found.", id)
                ));

        User dbUser = userRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User [%d] not found.", dto.getId())
                ));

        // check if user is already on the research
        for (User currentUser :
                dbItem.getUsers()) {
            if (Objects.equals(currentUser.getId(), dto.getId())) {
                throw new UserAlreadyAddedException(String.format(
                        "User [%d] is already part of the research [%d]",
                        dto.getId(),
                        dbItem.getId()
                ));
            }
        }

        dbItem.getUsers().add(dbUser);

        return ResearchParser.toDto(
                repository.save(dbItem)
        );
    }
    @Override
    public ResearchDto addUsers(Integer id, AddUsersRequest dto) {
        Research dbItem = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Research [%d] not found.", id)
                ));

        List<UserDto> dtos = dto.getUsers();
        for (UserDto item :
                dtos) {
            User dbUser = userRepository.findById(item.getId())
                    .orElseThrow(() -> new NotFoundException(String.format(
                            "User [%d] not found.", item.getId())
                    ));

            // check if user is already on the research
            boolean isAlreadyAdded = false;
            for (User currentUser :
                    dbItem.getUsers()) {
                if (Objects.equals(currentUser.getId(), item.getId())) {
                    LOGGER.warn(String.format(
                            "User [%d] is already part of the research [%d]",
                            item.getId(),
                            dbItem.getId()
                    ));

                    isAlreadyAdded = true;
                    break;
                }
            }

            if (!isAlreadyAdded) {
                dbItem.getUsers().add(dbUser);
            }
        }

        return ResearchParser.toDto(
                repository.save(dbItem)
        );
    }

    @Override
    public Object findAnimalStudiesByUserAndTrial(String documentNumber, Integer tid) {
        List<AnimalStudyDto> response = new ArrayList<>();

        User user = userRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with document number [%s] not found.", documentNumber)
                ));

        Trial dbItem = trialRepository.findById(tid)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Trial [%d] not found.", tid)
                ));

        Collection<Advance> advances = dbItem.getAdvances();
        if (advances == null || advances.isEmpty()) {
            LOGGER.warn(String.format("Trial [%d] has no advances associated.", tid));
        } else {
            for (Advance item :
                    advances) {
                if (item.getStage().equals(Stage.PRECLINICAL)) {
                    if (item.getAnimalStudy() != null) {
                        response.add(AnimalStudyParser.toDto(item.getAnimalStudy()));
                    }
                }
            }

            if (response.isEmpty()) {
                LOGGER.warn(String.format("Trial [%d] has no preclinical stage advances associated.", tid));
            }
        }

        return response;
    }

    @Override
    public Object findAnimalStudiesByUser(String documentNumber) {
        List<AnimalStudyDto> response = new ArrayList<>();

        userRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with document number [%s] not found.", documentNumber)
                ));

        List<Research> userResearches = this.findByUserDocumentNumberInternal(documentNumber);

        for (Research research :
                userResearches) {
            if (research.getTrials() == null || research.getTrials().isEmpty()) {
                continue;
            }

            for (Trial trial :
                    research.getTrials()) {
                if (trial.getAdvances() == null || trial.getAdvances().isEmpty()) {
                    continue;
                }

                for (Advance advance :
                        trial.getAdvances()) {
                    if (advance.getAnimalStudy() != null) {
                        AnimalStudyDto element = AnimalStudyParser.toDto(advance.getAnimalStudy());
                        element.setResearchId(research.getId());
                        element.setTrialId(trial.getId());
                        element.setAdvanceId(advance.getId());
                        response.add(element);
                    }
                }
            }
        }

        return response;
    }

    @Override
    public List<TrialDto> findTrialsByUserDocumentNumber(String key) {
        List<Research> researches = findByUserDocumentNumberInternal(key);
        List<TrialDto> response = new ArrayList<>();

        for (Research r :
                researches) {
            if (r.getTrials() != null && !r.getTrials().isEmpty()) {
                for (Trial t :
                        r.getTrials()) {
                    TrialDto dto = TrialParser.toDto(t);
                    dto.setResearchId(r.getId());
                    response.add(dto);
                }
            }
        }

        return response;
    }
}
