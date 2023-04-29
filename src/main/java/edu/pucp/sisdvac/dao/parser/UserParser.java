package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.domain.user.User;

public class UserParser {
    public static UserDto toDto(User input){
        return BaseParser.parse(input, UserDto.class);
    }

    public static User fromDto(UserDto input){
        return BaseParser.parse(input, User.class);
    }
}
