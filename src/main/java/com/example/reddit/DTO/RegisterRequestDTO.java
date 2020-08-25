package com.example.reddit.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class RegisterRequestDTO {

    @NotBlank( message = "Username cannot be blank!")
    private String username;

    @NotBlank( message = "Password cannot be blank!")
    private String password;

    @NotBlank( message = "Email cannot be blank!")
    @Email( message = "Email is not valid!")
    private String email;


    @JsonCreator
    public RegisterRequestDTO(@JsonProperty("username") String username,
                              @JsonProperty("password") String password,
                              @JsonProperty("email") String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
