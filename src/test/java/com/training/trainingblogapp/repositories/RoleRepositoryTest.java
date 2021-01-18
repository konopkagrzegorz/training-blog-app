package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void shouldFind3Authorites() {
        List<Role> roleList = roleRepository.findAll();
        assertEquals(3,roleList.size());
    }

    @Test
    void shouldFindAdminAuthority() {
        Optional<Role> role = roleRepository.findByName("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", role.get().getName());
    }

    @Test
    void shouldReturnEmptyAuthority() {
        Optional<Role> role = roleRepository.findByName("NOT_FOUND");
        assertEquals(Optional.empty(), role);
    }
}