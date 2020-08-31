package com.example.reddit.controller;

import com.example.reddit.DTO.PostRequest;
import com.example.reddit.DTO.PostResponse;
import com.example.reddit.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts() {
        List<PostResponse> postResponses = postService.getPosts();
        return new ResponseEntity<>(postResponses,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
    }

    @GetMapping("topic/{id}")
    public ResponseEntity<List<PostResponse>> getPostsByTopic(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostsByTopic(id), HttpStatus.OK);
    }

    @GetMapping("user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(String username) {
        return new ResponseEntity<>(postService.getPostsByUsername(username), HttpStatus.OK);
    }

}
