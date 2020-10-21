package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.model.PasswordGenerator;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.services.MailService;
import com.training.trainingblogapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @ModelAttribute ("userDTO")
    public UserDTO userDTO() {
        return new UserDTO();
    }


    @Autowired
    public MailController(MailService mailService, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.mailService = mailService;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @GetMapping("/reset-password")
    public String showResetPassword() {
        return "reset";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute UserDTO userDTO, BindingResult result) throws MessagingException {
        if (result.hasErrors()) {
            return "reset";
        }
        User user = userService.findByUsername(userDTO.getUsername());
         if (user == null) {
             throw new RuntimeException("Username does not exist");
         }

         if (user.getFirstName().equals(userDTO.getFirstName()) && user.getLastName().equals(userDTO.getLastName())
                    && user.getEmail().equals(userDTO.getEmail())) {

             PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                     .useDigits(true)
                     .useLower(true)
                     .useUpper(true)
                     .build();
             String password = passwordGenerator.generate(8);
             mailService.sendMail(user.getEmail(), "Password Reset",
                     "<h3>Message from Training Blog</h3><br>Your new password: " + "<strong>" + password + "</strong>", true);
             user.setPassword(bCryptPasswordEncoder.encode(password));
             userService.update(user);
         }
         return "reset";
    }
}
