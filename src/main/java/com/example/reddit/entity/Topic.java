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

    private String topicName;

    private String description;

    private Integer numberOfPosts;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOPIC_ID")
    private List<Post> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "USER_ID")
    private User user;

}
