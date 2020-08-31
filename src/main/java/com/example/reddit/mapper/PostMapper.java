package com.example.reddit.mapper;

import com.example.reddit.DTO.PostRequest;
import com.example.reddit.DTO.PostResponse;
import com.example.reddit.entity.Comment;
import com.example.reddit.entity.Post;
import com.example.reddit.entity.Topic;
import com.example.reddit.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper( componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "postDescription", source = "postRequest.postDescription")
    @Mapping(target = "topic", source = "topic")
    @Mapping(target = "url", source = "postRequest.url")
    @Mapping(target = "user", source = "user")
    Post mapToEntity(PostRequest postRequest, Topic topic, User user);

    @Mapping(target = "id", source = "post.id")
    @Mapping(target = "description", source = "post.postDescription")
    @Mapping(target = "url", source = "post.url")
    @Mapping(target = "topicName", source = "topic.topicName")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post.getComments()))")
    PostResponse mapToDto(Post post);

    default Integer commentCount(List<Comment> comments) {
        return comments.size();
    }


}
