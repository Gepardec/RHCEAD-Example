package com.gepardec.examples.rhcead.ejb;

import com.gepardec.examples.rhcead.dto.RoleDto;
import com.gepardec.examples.rhcead.jpa.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 1/1/2020
 */
public class RoleDtoTranslator {

    static List<Role> toRoles(final List<RoleDto> dtos) {
        return dtos.stream().map(RoleDtoTranslator::toRole).collect(Collectors.toList());
    }

    static Role toRole(final RoleDto dto) {
        return Role.valueOf(dto.name());
    }

    static List<RoleDto> toDtos(final Set<Role> dtos) {
        return dtos.stream().map(RoleDtoTranslator::toDto).collect(Collectors.toList());
    }

    static RoleDto toDto(final Role role) {
        return RoleDto.valueOf(role.name());
    }
}
