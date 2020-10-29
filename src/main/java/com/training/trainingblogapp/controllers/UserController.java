package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.dtos.UserPasswordDTO;
import com.training.trainingblogapp.domain.dtos.UserRegistrationDTO;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    @ModelAttribute("userRegistrationDto")
    public UserRegistrationDTO userRegistrationDto() {
        return new UserRegistrationDTO();
    }

    @ModelAttribute("userDTO")
    public UserDTO userDTO() {
        return new UserDTO();
    }

    @ModelAttribute("userPasswordDTO")
    public UserPasswordDTO userPasswordDTO() {
        return new UserPasswordDTO();
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile/edit")
    public String showEditProfile(Model model, Principal principal) {
        model.addAttribute("userDTO", userService.findByUsername(principal.getName()));
        return "profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@Valid @ModelAttribute ("userDTO") UserDTO userDTO, BindingResult result,
                              Principal principal, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "profile";
        }
        userService.update(userDTO,principal);
        attributes.addFlashAttribute("success", "Successfully updated you profile");
        return "redirect:/profile/edit";
    }

    @GetMapping("profile/edit/change-password")
    public String showChangePassword(Model model, Principal principal) {
        model.addAttribute("userPasswordDTO", userService.findUserPasswordDTOByUsername(principal.getName()));
        return "change-password";
    }

    @PostMapping("profile/edit/change-password")
    public String changePassword(@Valid @ModelAttribute ("userPasswordDTO") UserPasswordDTO userPasswordDTO,
                                 BindingResult result, Principal principal, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "change-password";
        }
        userService.changePassword(userPasswordDTO,principal);
        attributes.addFlashAttribute("success", "Successfully changed your password");
        return "redirect:/profile/edit/change-password";
    }

    @GetMapping("/terms")
    public String showTerms() {
        return "terms";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute ("userRegistrationDto") UserRegistrationDTO userRegistrationDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        User user = userService.save(userRegistrationDTO);
        return "index";
    }
}