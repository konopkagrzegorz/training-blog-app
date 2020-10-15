package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.MessageDTO;
import com.training.trainingblogapp.domain.model.Message;
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
}
