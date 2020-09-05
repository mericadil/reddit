package com.example.reddit.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "idgen", sequenceName = "REFRESH_TOKEN_SEQ")
public class RefreshToken extends BaseEntity{

    String token;
}

