package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.MessageDTO;
import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.dtos.UserRegistrationDTO;
import com.training.trainingblogapp.domain.model.Message;
import com.training.trainingblogapp.domain.model.Role;
import com.training.trainingblogapp.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    public Message messageDtoToMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setId(messageDTO.getId());
        message.setName(messageDTO.getName());
        message.setSubject(messageDTO.getSubject());
        message.setContactEmail(messageDTO.getContactEmail());
        message.setText(messageDTO.getText());

        return message;
    }

    public MessageDTO messageDtoToMessage(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setName(message.getName());
        messageDTO.setSubject(message.getSubject());
        messageDTO.setContactEmail(message.getContactEmail());
        messageDTO.setText(message.getText());

        return messageDTO;
    }

    public User userRegistrationDtoToUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword("");
        user.setEmail(userRegistrationDTO.getEmail());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());

        return user;
    }

    public User userDtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());

        return user;
    }
}
