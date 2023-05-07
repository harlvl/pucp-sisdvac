package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.ResearchDto;
import edu.pucp.sisdvac.controller.dto.UserDto;
import edu.pucp.sisdvac.domain.Research;
import edu.pucp.sisdvac.domain.user.User;

import java.util.ArrayList;
import java.util.List;

public class ResearchParser {
    public static Research fromDto(ResearchDto input) {
        Research output = BaseParser.parse(input, Research.class);
        if (input.getUsers() != null && !input.getUsers().isEmpty()) {
            List<User> users = new ArrayList<>();
            for (UserDto userDto :
                    input.getUsers()) {
                users.add(UserParser.fromDto(userDto));
            }
            output.setUsers(users);
        }
        return output;
    }

    public static ResearchDto toDto(Research input) {
        ResearchDto output = BaseParser.parse(input, ResearchDto.class);

        if (input.getUsers() != null && !input.getUsers().isEmpty()) {
            List<UserDto> userDtos = new ArrayList<>();
            for (User user :
                    input.getUsers()) {
                userDtos.add(UserParser.toDto(user));
            }
            output.setUsers(userDtos);

        }

        return output;
    }
}
