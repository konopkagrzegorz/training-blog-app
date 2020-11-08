package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.MessageDTO;
import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.dtos.UserPasswordDTO;
import com.training.trainingblogapp.domain.dtos.UserRegistrationDTO;
import com.training.trainingblogapp.domain.model.Message;
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
        message.setStatus(messageDTO.isStatus());

        return message;
    }

    public MessageDTO messageToDtoMessage(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setName(message.getName());
        messageDTO.setSubject(message.getSubject());
        messageDTO.setContactEmail(message.getContactEmail());
        messageDTO.setText(message.getText());
        messageDTO.setStatus(message.getStatus());

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

    public UserPasswordDTO userToUserPasswordDto(User user) {
        UserPasswordDTO userPasswordDTO = new UserPasswordDTO();
        userPasswordDTO.setId(user.getId());

        return userPasswordDTO;
    }

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public User userDtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        return user;
    }
}
