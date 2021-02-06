package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.MessageDTO;
import com.training.trainingblogapp.domain.model.Message;
import com.training.trainingblogapp.exceptions.InvalidInputException;
import com.training.trainingblogapp.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private MappingService mappingService;
    private ContactRepository contactRepository;

    @Autowired
    public ContactService(MappingService mappingService, ContactRepository contactRepository) {
        this.mappingService = mappingService;
        this.contactRepository = contactRepository;
    }

    public void addMessage(MessageDTO messageDTO) {
        Message message = null;
        message = mappingService.messageDtoToMessage(messageDTO);
        contactRepository.save(message);
    }


    public List<MessageDTO> findAll() {
        List<Message> messages = contactRepository.findAll();
        return messages.stream().map(message -> {
            MessageDTO messageDTO = mappingService.messageToDtoMessage(message);
            return messageDTO;
        }).collect(Collectors.toList());
    }

    public MessageDTO findById(Long id) {
        Optional<Message> message = contactRepository.findById(id);
        if (message.isPresent()) {
            MessageDTO messageDTO = mappingService.messageToDtoMessage(message.get());
            return messageDTO;
        } else {
            throw new InvalidInputException(String.format("Message with %d id doesn't exists", id));
        }
    }

    public void update(MessageDTO messageDTO) {
        Message message = mappingService.messageDtoToMessage(messageDTO);
        contactRepository.save(message);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
