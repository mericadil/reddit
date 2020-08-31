package com.example.reddit.controller;

import com.example.reddit.DTO.CommentDTO;
import com.example.reddit.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CommentDTO commentDTO) {
        commentService.save(commentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable Long postId) {
        List<CommentDTO> commentDTOS = commentService.getAllCommentsForPost(postId);
        return new ResponseEntity<>(commentDTOS, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForUser(@PathVariable String userName){
        List<CommentDTO> commentDTOS = commentService.getAllCommentsForUser(userName);
        return new ResponseEntity<>(commentDTOS, HttpStatus.CREATED);
    }
}
