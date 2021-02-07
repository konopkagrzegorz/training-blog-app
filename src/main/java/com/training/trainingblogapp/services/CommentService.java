package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.CommentDTO;
import com.training.trainingblogapp.domain.model.Comment;
import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.exceptions.InvalidInputException;
import com.training.trainingblogapp.repositories.CommentRepository;
import com.training.trainingblogapp.repositories.PostRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
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

    public Optional<CommentDTO> findById(Long id) {
        Optional<Comment> comment = Optional.ofNullable(commentRepository.findById(id)).get();
        Optional<CommentDTO> commentDTO = Optional.empty();
        if (comment.isPresent()) {
            commentDTO = Optional.ofNullable(mappingService.commentToCommentDto(comment.get()));
        }
        return commentDTO;
    }

    public void saveComment(CommentDTO commentDTO, Principal principal, Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Optional<Post> post = postRepository.findById(id);
        Optional<User> user = userRepository.findByUsername(principal.getName());
        if (!post.isPresent()) {
            throw new InvalidInputException("You can not add a comment to post which does not exist");
        } else if (!user.isPresent()) {
            throw new InvalidInputException("User with that username does not exist, cannot add a comment");
        }
        Comment comment = mappingService.commentDtoToComment(commentDTO);
        comment.setPost(post.get());
        comment.setUser(user.get());

        comment.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        postRepository.save(post.get());
        commentRepository.save(comment);
    }

    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    public void deleteByUser(String username) {
        Optional<User> temp = userRepository.findByUsername(username);
        commentRepository.deleteByUser(temp.get());
    }
}
