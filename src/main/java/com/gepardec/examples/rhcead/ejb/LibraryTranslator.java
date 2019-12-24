package com.gepardec.examples.rhcead.ejb;

import com.gepardec.examples.rhcead.dto.LibraryDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
public class LibraryTranslator {

    private LibraryTranslator() {
    }

    public static List<LibraryDto> toDto(final List<com.gepardec.examples.rhcead.jpa.Library> dtos) {
        return dtos.stream().map(LibraryTranslator::toDto).collect(Collectors.toList());
    }

    public static LibraryDto toDto(com.gepardec.examples.rhcead.jpa.Library entity) {
        final LibraryDto dto = new LibraryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }
}
