package com.example.reddit.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicDTO {
    private Long id;

    @NotBlank( message = "Topic name cannot be blank!")
    private String topicName;

    @NotBlank(message = "Topic description cannot be blank!")
    private String description;

    private Integer numberOfPosts;
}
