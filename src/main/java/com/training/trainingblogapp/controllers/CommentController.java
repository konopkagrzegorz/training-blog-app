package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.CommentDTO;
import com.training.trainingblogapp.exceptions.InvalidInputException;
import com.training.trainingblogapp.services.CommentService;
import com.training.trainingblogapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class CommentController {

    private CommentService commentService;
    private PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/post/{id}/add-comment")
    public String addComment(@PathVariable ("id") Long id, @Valid CommentDTO commentDTO, BindingResult result,
                             Model model, Principal principal) {
        Long tempId = id;
        if (result.hasErrors()) {
            model.addAttribute("postDTO", postService.findPostById(tempId));
            return "showPost";
        } else {
            commentDTO.setId(0);
            model.addAttribute("commentDTO", commentDTO);
            commentService.saveComment(commentDTO,principal,id);
            return "redirect:/post/{id}";
        }
    }

    @GetMapping("/post/{postId}/comment/delete/{id}")
    public String deleteComment(@PathVariable("postId") long postId, @PathVariable("id") long id) {
        if (commentService.findById(id).isPresent()) {
            commentService.deleteById(id);
            return "redirect:/post/{postId}";
        }
        else {
            throw new InvalidInputException("Comment with that id does not exist");
        }
    }
}
