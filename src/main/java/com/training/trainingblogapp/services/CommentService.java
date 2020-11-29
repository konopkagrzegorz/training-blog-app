package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.CommentDTO;
import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.domain.model.Comment;
import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.repositories.CommentRepository;
import com.training.trainingblogapp.repositories.PostRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private  MappingService mappingService;

    @Autowired
    public CommentService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, MappingService mappingService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mappingService = mappingService;
    }

    public List<CommentDTO> findAll() {
        List<Comment> comments = commentRepository.findAllByOrderByDateDesc();
        List<CommentDTO> commentsDTO = comments.stream().map(comment -> {
            CommentDTO commentDTO = mappingService.commentToCommentDto(comment);
            return commentDTO;
        }).collect(Collectors.toList());
        return commentsDTO;
    }

    public List<CommentDTO> findAllByPostId(Long id) {
        List<Comment> comments = commentRepository.findCommentByPost_IdOrderByDateDesc(id);
        List<CommentDTO> commentsDTO = comments.stream().map(comment -> {
            CommentDTO commentDTO = mappingService.commentToCommentDto(comment);
            return commentDTO;
        }).collect(Collectors.toList());
        return commentsDTO;
    }

    public void saveComment(CommentDTO commentDTO, Principal principal, Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Post post = postRepository.findById(id).get();
        User user = userRepository.findByUsername(principal.getName());
        Comment comment = mappingService.commentDtoToComment(commentDTO);

        comment.setPost(post);
        comment.setUser(user);

        comment.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        postRepository.save(post);
        commentRepository.save(comment);
    }
}
