package com.gepardec.examples.rhcead.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
public class BookDto implements Serializable {

    private Long id;

    @NotNull(message = "{name.null}")
    @Size(min = 1, max = 255, message = "{name.size}")
    private String name;

    @NotNull(message = "{libraryId.null}")
    @Min(value = 0, message = "{libraryId.min}")
    private Long libraryId;

    public BookDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Long libraryId) {
        this.libraryId = libraryId;
    }
}
