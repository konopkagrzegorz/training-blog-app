package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.dtos.UserPasswordDTO;
import com.training.trainingblogapp.domain.dtos.UserRegistrationDTO;
import com.training.trainingblogapp.domain.model.Role;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.repositories.RoleRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private MappingService mappingService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    private User user1 = new User
            (1, "User 1", "Firstname 1", "Last name 1",
                    "pw1" ,"mail1@mail.com",
                    new Role("ROLE_ADMIN"), new HashSet<>(), new HashSet<>());
    private UserDTO userDTO1 = new UserDTO(user1.getId(), user1.getUsername(), user1.getFirstName(),
            user1.getLastName(),user1.getEmail(),user1.getRole());
    private User user2 = new User
            (1, "User 2", "Firstname 2", "Last name 2",
                    "pw2" ,"mail2@mail.com",
                    new Role("ROLE_MODERATOR"), new HashSet<>(), new HashSet<>());
    private UserDTO userDTO2 = new UserDTO(user2.getId(), user2.getUsername(), user2.getFirstName(),
            user2.getLastName(),user2.getEmail(),user2.getRole());

    private UserPasswordDTO userPasswordDTO = new UserPasswordDTO(user1.getId(),user1.getPassword(),user1.getPassword());


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void shouldReturn2Users_findAll() {
        //when
        when(userRepository.findAll()).thenReturn(Lists.list(user1, user2));
        when(mappingService.userToUserDTO(user1)).thenReturn(userDTO1);
        when(mappingService.userToUserDTO(user2)).thenReturn(userDTO2);

        //then
        List<UserDTO> actual = userService.findAll();
        assertThat(actual).containsExactlyInAnyOrder(userDTO1,userDTO2);
    }

    @Test
    void shouldReturn1User_findById() {
        //when
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(mappingService.userToUserDTO(user1)).thenReturn(userDTO1);

        //then
        assertThat(userService.findById(user1.getId())).isEqualTo(userDTO1);
    }

    @Test
    void shouldReturnUser_findByUsername() {
        //when
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));
        when(mappingService.userToUserDTO(user1)).thenReturn(userDTO1);

        //then
        assertThat(userService.findByUsername(userDTO1.getUsername())).isEqualTo(Optional.of(userDTO1));
    }

    @Test
    void shouldReturnEmptyOptional_findByUsername() {
        //when
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.empty());

        //then
        assertThat(userService.findByUsername(userDTO1.getUsername())).isEmpty();
    }

    @Test
    void shouldReturnUser_findByEmail() {
        //when
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));

        //then
        assertThat(userService.findByUsername(userDTO1.getUsername())).isEqualTo(Optional.of(userDTO1));
    }

    @Test
    void shouldReturnUser_findUserPasswordDTOByUsername() {
        //when
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));
        when(mappingService.userToUserPasswordDto(user1)).thenReturn(userPasswordDTO);
        //then
        assertThat(userService.findUserPasswordDTOByUsername(user1.getUsername())).isEqualTo(userPasswordDTO);
    }

    @Test
    void save() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void update() {
    }

    @Test
    void update1() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void loadUserByUsername() {
    }
}