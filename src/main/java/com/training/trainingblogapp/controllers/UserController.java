package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.dtos.UserPasswordDTO;
import com.training.trainingblogapp.domain.dtos.UserRegistrationDTO;
import com.training.trainingblogapp.domain.model.PasswordGenerator;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.services.MailService;
import com.training.trainingblogapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;
    private MailService mailService;

    @ModelAttribute("userRegistrationDto")
    public UserRegistrationDTO userRegistrationDto() {
        return new UserRegistrationDTO();
    }

    @ModelAttribute ("userDTO")
    public UserDTO userDTO() {
        return new UserDTO();
    }

    @Autowired
    public UserController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
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

    @GetMapping("/reset-password")
    public String showResetPassword() {
        return "reset";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute UserDTO userDTO) throws MessagingException {

        User user = userService.findByUsername(userDTO.getUsername());
        if (user != null && user.getFirstName().equals(userDTO.getFirstName()) && user.getLastName().equals(userDTO.getLastName())
                && user.getEmail().equals(userDTO.getEmail())) {
            PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                    .useDigits(true)
                    .useLower(true)
                    .useUpper(true)
                    .build();
            String password = passwordGenerator.generate(8);
            mailService.sendMail(user.getEmail(), "Password Reset",
                    "<h3>Message from Training Blog</h3><br>Your new password: " + "<strong>" + password + "</strong>", true);
            userService.resetPassword(userDTO,password);
            return "redirect:/reset-password?success";
        }
        return "redirect:/reset-password?error";
    }

    @GetMapping("/admin/users/list")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users-list";
    }

    @GetMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable ("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users/list";
    }
}