package com.gepardec.examples.rhcead.jpa;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/30/2019
 */
@Entity
@Table(name = "USER")
@NamedQueries({
        @NamedQuery(name = "listAllUsers", query = "SELECT entity FROM User entity")
})
public class User implements Serializable {

    @Id
    @SequenceGenerator(name = "userSequence", sequenceName = "USER_SEQUENCE", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "userSequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", updatable = false, insertable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USERNAME", unique = true)
    private String username;

    @NotNull
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Email
    @Column(name = "EMAIL", unique = true)
    private String email;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FIRSTNAME")
    private String firstname;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "LASTNAME")
    private String lastname;

    @NotNull
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
    @CollectionTable(name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"))
    @Column(name = "ROLE", nullable = false)
    private Set<Role> roles = new HashSet<>(0);

    @OneToMany(mappedBy = "owner")
    private Set<Library> libraries = new HashSet<>(0);

    public User() {
    }

    @PrePersist
    void prePersist() {
        createdAt = updatedAt = LocalDateTime.now();
        email = (email != null) ? email.toLowerCase() : null;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
        email = (email != null) ? email.toLowerCase() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

        User user = (User) o;

        // If id is null than we compare instance id
        return (id == null) ? super.equals(o) : id == user.id;
    }

    @Override
    public int hashCode() {
        return (id == null) ? super.hashCode() : (int) (id ^ (id >>> 32));
    }
}
