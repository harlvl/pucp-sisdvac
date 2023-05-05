package edu.pucp.sisdvac.service;

import edu.pucp.sisdvac.controller.dto.UserDto;

import java.util.List;

public interface IUserService {
    UserDto findById(Integer id);
    UserDto findByEmail(String email);
    List<UserDto> findAll();
    UserDto update(Integer id, UserDto dto);
}
