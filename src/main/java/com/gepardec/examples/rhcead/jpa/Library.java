package com.gepardec.examples.rhcead.jpa;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@Entity
@Table(name = "LIBRARY")
@NamedQueries({
        @NamedQuery(name = "listAllLibraries", query = "SELECT entity FROM Library entity")
})
public class Library {

    @NotNull
    @Id
    @SequenceGenerator(name = "librarySequence", sequenceName = "LIBRARY_SEQUENCE", initialValue = 0, allocationSize = 1)
    @GeneratedValue(generator = "librarySequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", insertable = false, updatable = false)
    private Long id;

    @NotNull
    @Size(min = 0, max = 255)
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdDate;

    @NotNull
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedDate;

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "library")
    private Set<Book> books = new HashSet<>(0);

    public Library() {
    }

    @PrePersist
    void oreUpdate() {
        createdDate = updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        updatedDate = LocalDateTime.now();
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Library book = (Library) o;

        // If id is null than we compare instance id
        return (id == null) ? super.equals(o) : id == book.id;
    }

    @Override
    public int hashCode() {
        return (id == null) ? super.hashCode() : (int) (id ^ (id >>> 32));
    }
}
