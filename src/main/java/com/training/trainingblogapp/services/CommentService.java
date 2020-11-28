package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.CommentDTO;
import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.domain.model.Comment;
import com.training.trainingblogapp.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private  MappingService mappingService;

    @Autowired
    public CommentService(CommentRepository commentRepository, MappingService mappingService) {
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
}
