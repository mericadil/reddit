package com.example.reddit.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class LoginRequestDTO {

    @NotBlank( message = "Username cannot be blank!")
    private String username;

    @NotBlank( message = "Password cannot be blank!")
    private String password;

    @JsonCreator
    public LoginRequestDTO(@JsonProperty("username") String username,
                              @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
