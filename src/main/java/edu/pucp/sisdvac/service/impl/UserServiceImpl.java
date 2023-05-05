package edu.pucp.sisdvac.service.impl;

import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.controller.exception.NotFoundException;
import edu.pucp.sisdvac.dao.UserRepository;
import edu.pucp.sisdvac.dao.parser.BaseParser;
import edu.pucp.sisdvac.dao.parser.UserParser;
import edu.pucp.sisdvac.domain.user.User;
import edu.pucp.sisdvac.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository repository;

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
    public UserDto findByEmail(String email) {
        return UserParser.toDto(
                repository.findByEmail(email)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                "User with email %s not found.", email
                        )))
        );
    }

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
