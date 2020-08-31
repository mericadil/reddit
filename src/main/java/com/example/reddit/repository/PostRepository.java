package com.example.reddit.repository;

import com.example.reddit.entity.Post;
import com.example.reddit.entity.Topic;
import com.example.reddit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findById(Long id);

    Optional<List<Post>> findAllByTopic(Topic topic);

    Optional<List<Post>> findByUser(User user);
}
