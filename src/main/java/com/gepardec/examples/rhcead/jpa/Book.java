package com.gepardec.examples.rhcead.jpa;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@Entity()
@Table(name = "BOOK")
@NamedQueries({
        @NamedQuery(name = "listAllBooks", query = "SELECT entity FROM Book entity")
})
public class Book {

    @NotNull
    @Id
    @SequenceGenerator(name = "bookSequence", sequenceName = "BOOK_SEQUENCE", initialValue = 0, allocationSize = 1)
    @GeneratedValue(generator = "bookSequence", strategy = GenerationType.SEQUENCE)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIBRARY_ID", referencedColumnName = "ID")
    private Library library;

    public Book() {
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

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        // If id is null than we compare instance id
        return (id == null) ? super.equals(o) : id == book.id;
    }

    @Override
    public int hashCode() {
        return (id == null) ? super.hashCode() : (int) (id ^ (id >>> 32));
    }
}
