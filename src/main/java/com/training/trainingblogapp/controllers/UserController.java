package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.dtos.UserPasswordDTO;
import com.training.trainingblogapp.domain.dtos.UserRegistrationDTO;
import com.training.trainingblogapp.domain.model.PasswordGenerator;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.services.MailService;
import com.training.trainingblogapp.services.MappingService;
import com.training.trainingblogapp.services.RoleService;
import com.training.trainingblogapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;
    private MailService mailService;
    private RoleService roleService;
    private MappingService mappingService;

    @ModelAttribute("userRegistrationDto")
    public UserRegistrationDTO userRegistrationDto() {
        return new UserRegistrationDTO();
    }

    @Autowired
    public UserController(UserService userService, MailService mailService, RoleService roleService, MappingService mappingService) {
        this.userService = userService;
        this.mailService = mailService;
        this.roleService = roleService;
        this.mappingService = mappingService;
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
    public String register(@Valid @ModelAttribute ("userRegistrationDto") UserRegistrationDTO userRegistrationDTO, BindingResult result, Model model) {

        Optional<String> username = Optional.ofNullable(userService.findByUsername(userRegistrationDTO.getUsername()).get().getUsername());
        //Optional<String> email = Optional.ofNullable(userService.findByEmail(userRegistrationDTO.getEmail()).get().getEmail());
        model.addAttribute("userFound", userRegistrationDTO.getUsername());
        if (!(username.isEmpty())) {
            return "register";
        }
//        if (email != null) {
//            model.addAttribute("emailFound", email);
//            return "register";
//        }

        if (result.hasErrors()) {
            return "register";
        }
        userService.save(userRegistrationDTO);
        return "index";
    }

    @GetMapping("/reset-password")
    public String showResetPassword(@ModelAttribute UserDTO userDTO) {
        return "reset";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute UserDTO userDTO) throws MessagingException {

        User user = mappingService.userDtoToUser(userService.findByUsername(userDTO.getUsername()).get());
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

    @GetMapping("/admin/users/{id}/edit-role")
    public String showEditRole(@PathVariable ("id") Long id, Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("userDTO", userService.findById(id));
        return "user-role";
    }

    @PostMapping("/admin/users/{id}/edit-role")
    public String editRole(@ModelAttribute ("userDTO") UserDTO userDTO, BindingResult result) {
        userService.update(userDTO);
        return "redirect:/admin/users/list";
    }
}