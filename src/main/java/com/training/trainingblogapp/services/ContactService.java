package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.MessageDTO;
import com.training.trainingblogapp.domain.model.Message;
import com.training.trainingblogapp.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
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
        Message message = contactRepository.findById(id).get();
        MessageDTO messageDTO = mappingService.messageToDtoMessage(message);

        return messageDTO;
    }

    public void update(MessageDTO messageDTO) {
        Message message = mappingService.messageDtoToMessage(messageDTO);
        contactRepository.save(message);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
