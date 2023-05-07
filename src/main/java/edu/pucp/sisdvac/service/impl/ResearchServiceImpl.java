package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.ResearchDto;
import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.controller.exception.UserAlreadyAddedException;
import edu.pucp.sisdvac.dao.ResearchRepository;
import edu.pucp.sisdvac.dao.UserRepository;
import edu.pucp.sisdvac.dao.parser.BaseParser;
import edu.pucp.sisdvac.dao.parser.ResearchParser;
import edu.pucp.sisdvac.dao.parser.UserParser;
import edu.pucp.sisdvac.domain.Research;
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
}
