package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.TagDTO;
import com.training.trainingblogapp.exceptions.InvalidInputException;
import com.training.trainingblogapp.exceptions.NotUniqueException;
import com.training.trainingblogapp.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
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
        } else if (tagService.findByName(tagDTO.getName()).isPresent()) {
            throw new NotUniqueException("Tag with that name already exists");
        }
        tagService.add(tagDTO);
        return "redirect:/tags/showAll";
    }
}
