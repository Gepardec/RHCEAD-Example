package com.gepardec.examples.rhcead.jpa;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@Entity
@Table(name = "LIBRARY",
        uniqueConstraints = {
                @UniqueConstraint(name = "UQ_OWNER_ID_NAME",
                        columnNames = {"USER_ID", "NAME"})})
@NamedQueries({
        @NamedQuery(name = "listAllLibraries", query = "SELECT entity FROM Library entity"),
        @NamedQuery(name = "searchLibraryByName", query = "SELECT entity FROM Library entity WHERE entity.name = :name"),
        @NamedQuery(name = "searchLibrariesByBookName", query = "SELECT DISTINCT entity FROM Library entity INNER JOIN entity.books book WHERE lower(book.name) = lower(:bookName)")
})
public class Library {

    @NotNull
    @Id
    @SequenceGenerator(name = "librarySequence", sequenceName = "LIBRARY_SEQUENCE", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "librarySequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", insertable = false, updatable = false)
    private Long id;

    @NotNull
    @Size(min = 0, max = 255)
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinTable(name = "LIBRARY_BOOKS",
            joinColumns = @JoinColumn(name = "LIBRARY_ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID")
    )
    private Set<Book> books;

    public Library() {
    }

    @PrePersist
    void prePersist() {
        createdAt = updatedAt = LocalDateTime.now();
        name = (name != null) ? name.toLowerCase() : null;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdDate) {
        this.createdAt = createdDate;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedDate) {
        this.updatedAt = updatedDate;
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
