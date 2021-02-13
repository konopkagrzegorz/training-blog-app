package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.domain.dtos.TagDTO;
import com.training.trainingblogapp.services.PostService;
import com.training.trainingblogapp.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class TagController {

    private TagService tagService;
    private PostService postService;

    @Autowired
    public TagController(TagService tagService, PostService postService) {
        this.tagService = tagService;
        this.postService = postService;
    }

    @GetMapping("/tags/showAll")
    public String addTag(@ModelAttribute("tagDTO") TagDTO tagDTO, Model model) {
        model.addAttribute("tagsDTO", tagService.findAll());
        return "showTags";
    }

    @PostMapping("/tags/add-new")
    public String addTag(@Valid @ModelAttribute("tagDTO") TagDTO tagDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "showTags";
        }
        tagService.add(tagDTO);
        return "redirect:/tags/showAll";
    }

    @GetMapping("/admin/tags/delete/{id}")
    public String deleteTag(@PathVariable("id") long id, Principal principal) {
        Optional<TagDTO> tagDTO = tagService.findById(id);
        if (tagDTO.isPresent()) {
            List<PostDTO> posts = postService.findByTags_Id(id);

            for (PostDTO postDTO : posts) {
                postDTO.getTags().remove(tagDTO.get());
                postService.update(postDTO, principal);
            }
            tagService.deleteById(id, principal);
        }
        return "redirect:/";
    }
}