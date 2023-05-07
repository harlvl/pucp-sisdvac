package edu.pucp.sisdvac.service;

import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.domain.user.Role;

import java.util.List;

public interface IUserService {
    List<UserDto> findAll();
    List<UserDto> findByRole(Role role);
    UserDto findById(Integer id);
    UserDto findByEmail(String key);
    UserDto findByDocumentNumber(String key);
    List<UserDto> findByName(String key);
    UserDto save(UserDto dto);
    UserDto update(Integer id, UserDto dto);
}
