package com.example.reddit.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="\"user\"")
@SequenceGenerator(name = "idgen", sequenceName = "USER_SEQ")
public class User extends BaseEntity{

    @NotBlank( message = "Username cannot be blank!")
    @Column( unique = true)
    private String username;

    @NotBlank( message = "Password cannot be blank!")
    private String password;

    @NotBlank( message = "Email cannot be blank!")
    @Email( message = "Email is not valid!")
    @Column( unique = true)
    private String email;


    private boolean enabled;
}
