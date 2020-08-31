package com.example.reddit.repository;


import com.example.reddit.entity.Comment;
import com.example.reddit.entity.Post;
import com.example.reddit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByUser(User user);
}
