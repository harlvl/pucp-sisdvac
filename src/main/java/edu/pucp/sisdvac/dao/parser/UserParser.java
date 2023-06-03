package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.domain.user.User;
import edu.pucp.sisdvac.security.auth.RegisterRequest;

public class UserParser {
    public static UserDto toDto(User input){
        UserDto output = BaseParser.parse(input, UserDto.class);
        if (input.getId() != null) {
            output.setId(input.getId());
        }

        return output;
    }

    public static User fromDto(UserDto input){
        User output = BaseParser.parse(input, User.class);
        if (input.getId() != null) {
            output.setId(input.getId());
        }
        return output;
    }

    public static User fromRequest(RegisterRequest input) {
        return BaseParser.parse(input, User.class);
    }
}
