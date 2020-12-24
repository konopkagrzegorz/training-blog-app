package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.AnswerDTO;
import com.training.trainingblogapp.domain.dtos.MessageDTO;
import com.training.trainingblogapp.services.ContactService;
import com.training.trainingblogapp.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
public class ContactController {

    private ContactService contactService;
    private MailService mailService;

    @ModelAttribute("answerDTO")
    public AnswerDTO answerDTO() {
        return new AnswerDTO();
    }

    @Autowired
    public ContactController(ContactService contactService, MailService mailService) {
        this.contactService = contactService;
        this.mailService = mailService;
    }

    @ModelAttribute
    public MessageDTO messaggeDTO() {
        return new MessageDTO();
    }

    @GetMapping("/admin/messages/list")
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

    @GetMapping("/admin/messages/delete/{id}")
    public String deleteMessage(@PathVariable("id") Long id) {
        contactService.delete(id);
        return "redirect:/admin/messages/list";
    }

    @GetMapping("admin//messages/show/{id}")
    public String showMessageDetails (@PathVariable ("id") Long id, Model model) {
        MessageDTO messageDTO = contactService.findById(id);
        model.addAttribute("messageDTO", messageDTO);
        return "message-details";
    }

    @GetMapping("/admin/messages/answer/{id}")
    public String showAnswerMessage(@PathVariable ("id") Long id, Model model) {
        MessageDTO messageDTO = contactService.findById(id);
        model.addAttribute("messageDTO", messageDTO);
        return "message-answer";
    }

    @PostMapping("/admin/messages/answer/{id}")
    public String answerMessage(@PathVariable ("id") Long id,@Valid @ModelAttribute("answerDTO") AnswerDTO answerDTO, BindingResult result) {
        MessageDTO messageDTO = contactService.findById(id);
        if (result.hasErrors()) {
            return "message-answer";
        } else {
            try {
                mailService.sendMail(messageDTO.getContactEmail(), messageDTO.getSubject(),
                        answerDTO.getText(), false);
                messageDTO.setStatus(true);
                contactService.update(messageDTO);
            } catch (MessagingException exception) {
                exception.printStackTrace();
            }
        }
        return "redirect:/admin/messages/answer/{id}";
    }
}
