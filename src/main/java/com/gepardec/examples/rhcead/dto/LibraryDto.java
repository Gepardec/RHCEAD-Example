package com.gepardec.examples.rhcead.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
public class LibraryDto {

    private Long id;

    @NotNull(message = "{name.null}")
    @Size(min = 1, max = 255, message = "{name.size}")
    private String name;

    public LibraryDto() {
    }

    public LibraryDto(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
