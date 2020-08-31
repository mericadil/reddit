package com.example.reddit.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String topicName;
    private String postName;
    private String url;
    private String postDescription;
}
