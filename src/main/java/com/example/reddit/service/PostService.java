package com.example.reddit.service;

import com.example.reddit.DTO.PostRequest;
import com.example.reddit.DTO.PostResponse;
import com.example.reddit.entity.Post;
import com.example.reddit.entity.Topic;
import com.example.reddit.entity.User;
import com.example.reddit.mapper.PostMapper;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.TopicRepository;
import com.example.reddit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final AuthService authService;

    public void save(PostRequest postRequest) {
        Topic topic = topicRepository.findByTopicName(postRequest.getTopicName())
                .orElseThrow(() -> new EntityNotFoundException());
        postRepository.save(postMapper.mapToEntity(postRequest, topic, authService.getCurrentUser()));
    }

    public List<PostResponse> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException());
        return postMapper.mapToDto(post);
    }

    public List<PostResponse> getPostsByTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        List<Post> posts = postRepository.findAllByTopic(topic)
                .orElseThrow( () -> new EntityNotFoundException());
        return posts
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException());
        List<Post> posts = postRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException());
        return posts
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
