package com.example.reddit.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "idgen", sequenceName = "TOPIC_SEQ")
public class Topic extends BaseEntity {

    @NotBlank( message = "Topic name cannot be blank!")
    private String topicName;

    @NotBlank(message = "Topic description cannot be blank!")
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOPIC_ID")
    private List<Post> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "USER_ID")
    private User user;

}
