package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.domain.model.Role;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.repositories.PostRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.assertj.core.util.Lists;
import org.hibernate.engine.spi.Mapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {

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

    private Post post1 = new Post(1, "Heading 1", "Text 1",
            LocalDateTime.of(2020,10,20,16,30),
            new HashSet<>(), new HashSet<>(), null, user1);
    private Post post2 = new Post(2, "Heading 2", "Text 2",
            LocalDateTime.of(2020,2,20,16,30),
            new HashSet<>(), new HashSet<>(), null, user1);
    private Post post3 = new Post(3, "Heading 3", "Text 3",
            LocalDateTime.of(2021,1,9,16,30),
            new HashSet<>(), new HashSet<>(), null, user2);
    private PostDTO postDTO1 = new PostDTO(post1.getId(),post1.getHeading(),post1.getText(),
            new HashSet<>(), post1.getDate(),null,"",userDTO1);
    private PostDTO postDTO2 = new PostDTO(post2.getId(),post2.getHeading(),post2.getText(),
            new HashSet<>(), post2.getDate(),null,"",userDTO1);
    private PostDTO postDTO3 = new PostDTO(post3.getId(),post3.getHeading(),post3.getText(),
            new HashSet<>(), post3.getDate(),null,"",userDTO2);

    @Mock
    private UserRepository userRepository;

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Disabled
    void findAll() {
        //when
        Mockito.when(postRepository.findAll()).thenReturn(Lists.list(post1,post2,post3));
        Mockito.when(mappingService.postToPostDto(post1)).thenReturn(postDTO1);
        Mockito.when(mappingService.postToPostDto(post2)).thenReturn(postDTO2);
        Mockito.when(mappingService.postToPostDto(post3)).thenReturn(postDTO3);

        //them
        List<PostDTO> actual = postService.findAll();
        assertThat(actual).containsExactlyInAnyOrder(postDTO1,postDTO2,postDTO3);
    }

    @Test
    @Disabled
    void listAllByPage() {
    }

    @Test
    @Disabled
    void findAllPostContainsPhase() {
    }

    @Test
    @Disabled
    void findByTags_Id() {
    }

    @Test
    @Disabled
    void deleteById() {
    }

    @Test
    @Disabled
    void findById() {
    }

    @Test
    @Disabled
    void deleteByUser() {
    }

    @Test
    @Disabled
    void findPostById() {
    }

    @Test
    @Disabled
    void update() {
    }

    @Test
    @Disabled
    void addPost() {
    }
}