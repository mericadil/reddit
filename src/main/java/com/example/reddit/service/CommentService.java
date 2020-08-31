package com.example.reddit.service;

import com.example.reddit.DTO.CommentDTO;
import com.example.reddit.entity.Comment;
import com.example.reddit.entity.Post;
import com.example.reddit.entity.User;
import com.example.reddit.mapper.CommentMapper;
import com.example.reddit.repository.CommentRepository;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    public void save(CommentDTO commentDTO) {
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new EntityNotFoundException());

        Comment comment = commentMapper.map(commentDTO, post, authService.getCurrentUser());
        List<Comment> comments = post.getComments();
        comments.add(comment);

        commentRepository.save(comment);
        postRepository.save(post);
    }

    public List<CommentDTO> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException());

        return commentRepository.findAllByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentDTO> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow( () -> new EntityNotFoundException());

        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }
}
