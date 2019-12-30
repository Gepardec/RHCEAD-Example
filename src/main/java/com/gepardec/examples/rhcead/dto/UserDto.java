package com.gepardec.examples.rhcead.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/30/2019
 */
public class UserDto {

    @NotNull
    @Size(min = 2, max = 50, message = "{user.username.notnull}")
    private String username;

    @NotNull
    @Size(min = 8, max = 100, message = "{user.password.notnull}")
    private String password;

    @NotNull
    @Size(min = 2, max = 100, message = "{user.firstname.notnull}")
    private String firstname;

    @NotNull
    @Size(min = 2, max = 100, message = "{user.lastname.notnull}")
    private String lastname;

    @NotNull(message = "{user.email.notnull}")
    @Email(message = "{user.email.email}")
    private String email;

    public UserDto() {
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
}
