package com.gepardec.examples.rhcead.ejb;

import com.gepardec.examples.rhcead.dto.UserDto;
import com.gepardec.examples.rhcead.jpa.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
public class UserTranslator {

    private UserTranslator() {
    }

    static List<UserDto> toDtos(final List<User> entities) {
        return entities.stream().map(UserTranslator::toDto).collect(Collectors.toList());
    }

    static UserDto toDto(User entity) {
        final UserDto dto = new UserDto();
        dto.setUsername(entity.getUsername());
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());
        dto.setEmail(entity.getEmail());
        dto.setRoles(RoleDtoTranslator.toDtos(entity.getRoles()));

        return dto;
    }
}
