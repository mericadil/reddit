package com.example.reddit.controller;

import com.example.reddit.DTO.TopicDTO;
import com.example.reddit.repository.TopicRepository;
import com.example.reddit.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topic")
@RequiredArgsConstructor
@Validated
public class TopicController {

    private final TopicRepository topicRepository;
    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody @Valid TopicDTO topicDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(topicService.save(topicDTO));
    }

    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAllTopics() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(topicService.getAllTopics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> getTopic(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(topicService.getTopic(id));
    }
}
