package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.services.MailService;
import com.training.trainingblogapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;

@Controller
public class MailController {

    private MailService mailService;
    private UserService userService;

    @ModelAttribute ("userDTO")
    UserDTO userDTO() {
        return new UserDTO();
    }

    @Autowired
    public MailController(MailService mailService, UserService userService) {
        this.mailService = mailService;
        this.userService = userService;
    }

    @GetMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "reset";
        }
        if (userService.loadUserByUsername(userDTO))
    }
}
