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
@Entity()
@Table(name = "BOOK")
@NamedQueries({
        @NamedQuery(name = "listAllBooks", query = "SELECT entity FROM Book entity"),
        @NamedQuery(name = "searchBookByName", query = "SELECT entity FROM Book entity WHERE entity.name = :name"),
        @NamedQuery(name = "searchBookByLibraryId", query = "SELECT entity FROM Book entity INNER JOIN entity.libraries library WHERE library.id = :libraryId"),
        @NamedQuery(name = "searchBookByUsername", query = "SELECT entity FROM Book entity INNER JOIN entity.libraries library INNER JOIN library.owner user WHERE library.name = lower(:libraryName) AND lower(user.username) = lower(:username)")
})
public class Book {

    @NotNull
    @Id
    @SequenceGenerator(name = "bookSequence", sequenceName = "BOOK_SEQUENCE", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "bookSequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", insertable = false, updatable = false)
    private Long id;

    @NotNull
    @Size(min = 0, max = 255)
    @Column(name = "NAME", unique = true)
    private String name;

    @NotNull
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdDate;

    @NotNull
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedDate;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "books")
    private Set<Library> libraries = new HashSet<>(0);

    public Book() {
    }

    @PrePersist
    void prePersist() {
        createdDate = updatedDate = LocalDateTime.now();
        name = (name != null) ? name.toLowerCase() : null;
    }

    @PreUpdate
    void preUpdate() {
        updatedDate = LocalDateTime.now();
        name = (name != null) ? name.toLowerCase() : null;
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

    public Set<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(Set<Library> libraries) {
        this.libraries = libraries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        // If id is null than we compare instance id
        return (id == null) ? super.equals(o) : id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return (id == null) ? super.hashCode() : (int) (id ^ (id >>> 32));
    }
}
