package com.example.reddit.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "verificationtoken")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "VERIFICATION_TOKEN_SEQ")
public class VerificationToken extends BaseEntity{

    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "USER_ID")
    private User user;

    private LocalDate expirationDate;
}
