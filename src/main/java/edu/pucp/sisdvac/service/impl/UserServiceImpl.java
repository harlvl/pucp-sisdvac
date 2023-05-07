package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.dao.UserRepository;
import edu.pucp.sisdvac.dao.parser.BaseParser;
import edu.pucp.sisdvac.dao.parser.UserParser;
import edu.pucp.sisdvac.domain.user.Role;
import edu.pucp.sisdvac.domain.user.User;
import edu.pucp.sisdvac.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository repository;

    @Override
    public List<UserDto> findAll() {
        List<UserDto> result = new ArrayList<>();

        List<User> dbItems = repository.findAll();
        for (User item :
                dbItems) {
            result.add(
                    UserParser.toDto(item)
            );
        }

        return result;
    }

    @Override
    public List<UserDto> findByRole(Role role) {
        List<UserDto> output = new ArrayList<>();
        List<User> dbItems = repository.findByRole(role);

        for (User item :
                dbItems) {
            output.add(
                    UserParser.toDto(item)
            );
        }

        return output;
    }

    @Override
    public UserDto findById(Integer id) {
        return UserParser.toDto(
                repository.findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "User with ID %d not found.", id
                        )))
        );
    }

    @Override
    public UserDto findByEmail(String key) {
        return UserParser.toDto(
                repository.findByEmail(key)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "User with email %s not found.", key
                        )))
        );
    }

    @Override
    public UserDto findByDocumentNumber(String key) {
        return UserParser.toDto(
                repository.findByDocumentNumber(key)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "User with document number %s not found.", key
                        )))
        );
    }

    @Override
    public List<UserDto> findByName(String key) {
        List<UserDto> output = new ArrayList<>();
        key = key.strip();

        List<User> dbItems = repository.findByFirstNameEqualsIgnoreCase(key);
        if (dbItems != null && !dbItems.isEmpty()) {
            for (User item :
                    dbItems) {
                output.add(UserParser.toDto(item));
            }
        }

        dbItems = repository.findByLastNameEqualsIgnoreCase(key);
        if (dbItems != null && !dbItems.isEmpty()) {
            for (User item :
                    dbItems) {
                output.add(UserParser.toDto(item));
            }
        }

        if (output.isEmpty()) {
            throw new NotFoundException(String.format(
                    "No users with first name or last name [%s] were found found.", key
            ));
        }

        return output;
    }

    @Override
    public UserDto save(UserDto dto) {
        LOGGER.info("Creating new user...");
        return UserParser.toDto(
                repository.save(
                        UserParser.fromDto(dto)
                )
        );
    }

    @Override
    public UserDto update(Integer id, UserDto dto) {
        LOGGER.info(String.format(
                "Updating user with ID %d", id
        ));
        User dbItem = repository.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                        "User with ID %d not found.", id
                ))
        );

        return UserParser.toDto(
                repository.save(
                        BaseParser.copyProperties(dto, dbItem)
                )
        );
    }

}
