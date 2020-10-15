package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.MessageDTO;
import com.training.trainingblogapp.domain.model.Message;
import com.training.trainingblogapp.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
