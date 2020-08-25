package com.example.reddit.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "idgen", sequenceName = "POST_SEQ")
public class Post extends BaseEntity{

    @NotBlank( message = "Post name cannot be blank!")
    private String postName;

    @Nullable
    private String url;

    @Nullable
    @Lob
    private String postDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "TOPIC_ID", referencedColumnName = "ID")
    private Topic topic;
}
