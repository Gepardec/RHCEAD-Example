package com.gepardec.examples.rhcead.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/30/2019
 */
public class UserDto {

    private Long id;

    @NotNull(message = "{user.username.notnull}")
    @Size(min = 2, max = 50, message = "{user.username.size}")
    private String username;

    @NotNull(message = "{user.password.notnull}")
    @Size(min = 8, max = 100, message = "{user.password.size}")
    private String password;

    @NotNull(message = "{user.firstname.notnull}")
    @Size(min = 2, max = 100, message = "{user.firstname.size}")
    private String firstname;

    @NotNull(message = "{user.lastname.notnull}")
    @Size(min = 2, max = 100, message = "{user.lastname.size}")
    private String lastname;

    @NotNull(message = "{user.email.notnull}")
    @Email(message = "{user.email.email}")
    private String email;

    private List<RoleDto> roles;

    public UserDto() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
}
