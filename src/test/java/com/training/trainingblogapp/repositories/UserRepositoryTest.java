package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    private static final String EXISTING_USERNAME = "gkonopka";
    private static final String NOTEXISTING_USERNAME = "bamboleo";

    private static final String EXISTING_EMAIL = "billgates@hotmail.com";
    private static final String NOTEXISTING_EMAIL = "bamboleo@gmail.com";
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturn10Users_findAll() {
        assertEquals(10,userRepository.findAll().size());
    }

    @Test
    public void shouldReturn1User_findByUsername() {
        Optional<User> user = userRepository.findByUsername(EXISTING_USERNAME);
        assertEquals(true, user.isPresent());
    }

    @Test
    public void shouldReturn0User_findByUsername() {
        Optional<User> user = userRepository.findByUsername(NOTEXISTING_USERNAME);
        assertEquals(false, user.isPresent());
    }

    @Test
    public void shouldReturn0User_findByEmail() {
        Optional<User> user = userRepository.findByEmail(NOTEXISTING_EMAIL);
        assertEquals(false, user.isPresent());
    }

}