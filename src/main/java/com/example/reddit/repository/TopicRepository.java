package com.example.reddit.repository;

import com.example.reddit.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TopicRepository extends JpaRepository<Topic,Long> {
    Optional<Topic> findById(Long id);
    Optional<Topic> findByTopicName(String topicName);
}
