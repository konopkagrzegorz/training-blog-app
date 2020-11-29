package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.CommentDTO;
import com.training.trainingblogapp.domain.model.Comment;
import com.training.trainingblogapp.repositories.CommentRepository;
import com.training.trainingblogapp.repositories.PostRepository;
import com.training.trainingblogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/post/{id}/add-comment")
    public String addComment(@PathVariable ("id") Long id,CommentDTO commentDTO, Model model,
                             Principal principal) {
        commentDTO.setId(0);
        model.addAttribute("commentDTO", commentDTO);
        commentService.saveComment(commentDTO,principal,id);
        return "redirect:/post/{id}";
    }
}
