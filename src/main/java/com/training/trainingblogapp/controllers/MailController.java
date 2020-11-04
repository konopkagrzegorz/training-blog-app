package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.model.PasswordGenerator;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.services.MailService;
import com.training.trainingblogapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.mail.MessagingException;
import javax.validation.Valid;


@Controller
public class MailController {

    private MailService mailService;
    private UserService userService;




    @Autowired
    public MailController(MailService mailService, UserService userService) {
        this.mailService = mailService;
        this.userService = userService;

    }
}
