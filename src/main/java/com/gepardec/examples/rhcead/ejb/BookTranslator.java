package com.gepardec.examples.rhcead.ejb;

import com.gepardec.examples.rhcead.dto.BookDto;
import com.gepardec.examples.rhcead.jpa.Book;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
public class BookTranslator {

    private BookTranslator() {
    }

    static List<BookDto> toDto(final List<Book> entities) {
        return entities.stream().map(BookTranslator::toDto).collect(Collectors.toList());
    }

    static BookDto toDto(Book entity) {
        final BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLibraryId(entity.getLibrary().getId());

        return dto;
    }
}
