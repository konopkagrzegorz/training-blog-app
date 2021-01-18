package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void shouldReturn70Messages() {
        List<Message> messagesList = contactRepository.findAll();
        assertEquals(70, messagesList.size());
    }
}