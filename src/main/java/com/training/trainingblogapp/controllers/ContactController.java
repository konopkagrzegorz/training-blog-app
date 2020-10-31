package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.MessageDTO;
import com.training.trainingblogapp.domain.model.Message;
import com.training.trainingblogapp.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ContactController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/messages/list")
    public String viewMessagesList(Model model) {
        model.addAttribute("messages", contactService.findAll());
        return "messages-list";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        MessageDTO messageDTO = new MessageDTO();
        model.addAttribute("messageDTO", messageDTO);
        return "contact";
    }

    @PostMapping("/contact/add-new")
    public String addMessage(@Valid @ModelAttribute ("messageDTO") MessageDTO messageDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "contact";
        }
        contactService.addMessage(messageDTO);
        return "redirect:/";
    }

    @GetMapping("/messages/delete/{id}")
    public String deleteMessage(@PathVariable("id") Long id) {
        contactService.delete(id);
        return "redirect:/messages/list";
    }
}
