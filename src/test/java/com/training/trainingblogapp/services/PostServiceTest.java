package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.domain.dtos.TagDTO;
import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.domain.model.Role;
import com.training.trainingblogapp.domain.model.Tag;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.exceptions.InvalidInputException;
import com.training.trainingblogapp.exceptions.UserNotAuthorizedException;
import com.training.trainingblogapp.repositories.PostRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    void shouldReturn3Posts_findAll() {
        //when
        when(postRepository.findAll()).thenReturn(Lists.list(post1,post2,post3));
        when(mappingService.postToPostDto(post1)).thenReturn(postDTO1);
        when(mappingService.postToPostDto(post2)).thenReturn(postDTO2);
        when(mappingService.postToPostDto(post3)).thenReturn(postDTO3);

        //them
        List<PostDTO> actual = postService.findAll();
        assertThat(actual).containsExactlyInAnyOrder(postDTO1,postDTO2,postDTO3);
    }

    @Test
    @Disabled
    void listAllByPage() {
    }

    @Test
    void shouldReturn3Posts_findAllPostContainsPhase() {
        //when
        when(postRepository.findAllPostContainsPhase("te")).
                thenReturn(Lists.list(post1,post2,post3));
        when(mappingService.postToPostDto(post1)).thenReturn(postDTO1);
        when(mappingService.postToPostDto(post2)).thenReturn(postDTO2);
        when(mappingService.postToPostDto(post3)).thenReturn(postDTO3);

        //then
        List<PostDTO> actual = postService.findAllPostContainsPhase("te");
        assertThat(actual).containsExactlyInAnyOrder(postDTO1,postDTO2,postDTO3);

    }

    @Test
    void shouldReturn2Posts_findByTags_Id() {
        //given
        Tag tag = new Tag(1, "", new HashSet<>());
        TagDTO tagDTO = new TagDTO(tag.getId(), tag.getName(), new HashSet<>());

        post1.getTags().add(tag);
        post2.getTags().add(tag);
        postDTO1.getTags().add(tagDTO);
        postDTO2.getTags().add(tagDTO);

        //when
        when(postRepository.findByTags_Id(tag.getId())).thenReturn(Lists.list(post1,post2));
        when(mappingService.tagToTagDto(tag)).thenReturn(tagDTO);
        when(mappingService.postToPostDto(post1)).thenReturn(postDTO1);
        when(mappingService.postToPostDto(post2)).thenReturn(postDTO2);

        //then
        List<PostDTO> actual = postService.findByTags_Id(tag.getId());
        assertThat(actual).containsExactlyInAnyOrder(postDTO1,postDTO2);
    }

    @Test
    void shouldDeletePost_deleteById() {
        //given
        Long id=1L;
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return user1.getUsername();
            }
        };
        //when
        when(userRepository.findByUsername(principal.getName())).thenReturn(Optional.of(user1));
        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);

        doNothing().when(postRepository).deleteById(idCapture.capture());
        postService.deleteById(id,principal);

        assertEquals(1L, idCapture.getValue());
        verify(postRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldThrowUnauthorizedException_deleteById() {
        //given
        Long id=1L;
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return user2.getUsername();
            }
        };
        //when
        when(userRepository.findByUsername(principal.getName())).thenReturn(Optional.of(user2));
        doNothing().when(postRepository).deleteById(id);
        assertThrows(UserNotAuthorizedException.class, () -> postService.deleteById(id,principal));
    }

    @Test
    void ShouldReturn1Post_findById() {
        //when
        when(postRepository.findById(post1.getId())).thenReturn(Optional.of(post1));
        when(mappingService.postToPostDto(post1)).thenReturn(postDTO1);
        Optional<PostDTO> actual = postService.findById(post1.getId());
        assertThat(actual).isEqualTo(postDTO1);
    }

    @Test
    void ShouldThrowInvalidInputException_findById() {
        //when
        when(postRepository.findById(post1.getId())).thenReturn(Optional.empty());
        //then
        assertThrows(InvalidInputException.class, () -> postService.findById(post1.getId()));
    }

    @Test
    void shouldDeletePostsByUser_deleteByUser() {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return user1.getUsername();
            }
        };
        //when
        when(userRepository.findByUsername(principal.getName())).thenReturn(Optional.of(user1));
        when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));
        doNothing().when(postRepository).deleteByUser(user2);
        postService.deleteByUser(user2.getUsername(), principal);
        verify(postRepository, times(1)).deleteByUser(user2);
    }

    @Test
    void shouldThrowUnauthorizedException_deleteByUser() {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return user2.getUsername();
            }
        };
        //when
        when(userRepository.findByUsername(principal.getName())).thenReturn(Optional.of(user2));
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        doNothing().when(postRepository).deleteByUser(user2);
        assertThrows(UserNotAuthorizedException.class, () -> postService.deleteByUser(user1.getUsername(),principal));
    }

    @Test
    void shouldAllow_update() throws IOException {
        //given
        String heading = "Update heading";
        String text = "Update text";

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return user1.getUsername();
            }
        };

        //when
        given(postRepository.save(post3)).willReturn(post3);
        when(userRepository.findByUsername(user2.getUsername())).thenReturn(Optional.of(user2));
        when(userRepository.findByUsername(principal.getName())).thenReturn(Optional.of(user1));
        when(mappingService.postToPostDto(post3)).thenReturn(postDTO3);
        when(mappingService.updatePostDtoToPost(postDTO3)).thenReturn(post3);
        when(postRepository.findById(post3.getId())).thenReturn(Optional.of(post3));

        //then
        postDTO3.setHeading(heading);
        postDTO3.setText(text);
        postService.update(postDTO3,principal);
        assertThat(postDTO3.getHeading()).isEqualTo(heading);
        assertThat(postDTO3.getText()).isEqualTo(text);
        verify(postRepository,times(1)).save(post3);
    }

    @Test
    void shouldThrowUserNotAuthorizedException_update() throws IOException {
        //given
        String heading = "Update heading";
        String text = "Update text";

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return user2.getUsername();
            }
        };
        //when
        given(postRepository.save(post1)).willReturn(post1);
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));
        when(userRepository.findByUsername(principal.getName())).thenReturn(Optional.of(user2));
        when(mappingService.postToPostDto(post1)).thenReturn(postDTO1);
        when(mappingService.updatePostDtoToPost(postDTO1)).thenReturn(post1);
        when(postRepository.findById(post1.getId())).thenReturn(Optional.of(post1));

        //then
        postDTO1.setHeading(heading);
        postDTO1.setText(text);
        assertThrows(UserNotAuthorizedException.class, () -> postService.update(postDTO1,principal));
    }

    @Test
    void shouldThrowInvalidInputException_update() throws IOException {
        //given
        String heading = "Update heading";
        String text = "Update text";

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return user2.getUsername();
            }
        };
        //when
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));
        when(userRepository.findByUsername(principal.getName())).thenReturn(Optional.of(user1));
        when(mappingService.postToPostDto(post1)).thenReturn(postDTO1);
        when(mappingService.updatePostDtoToPost(postDTO1)).thenReturn(post1);
        when(postRepository.findById(post1.getId())).thenReturn(Optional.empty());

        //then
        postDTO1.setHeading(heading);
        postDTO1.setText(text);
        assertThrows(InvalidInputException.class, () -> postService.update(postDTO1,principal));

    }

    @Test
    void shouldAllow_addPost() throws IOException {
        //given
        Post post = new Post(10,"New heading", "New Text",LocalDateTime.now(), new HashSet<>(), new HashSet<>(),null, user1);
        PostDTO postDTO = new PostDTO(post.getId(), post.getHeading(),post.getText(),new HashSet<>(), post.getDate(),null, "", userDTO1);

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return user1.getUsername();
            }
        };
        //when
        when(postRepository.save(post)).thenReturn(post);
        when(mappingService.postToPostDto(post)).thenReturn(postDTO);
        when(mappingService.newPostDtoToPost(postDTO)).thenReturn(post);
        when(userRepository.findByUsername(principal.getName())).thenReturn(Optional.of(user1));
        given(postRepository.save(post)).willAnswer(invocation -> invocation.getArgument(0));
        //then
        postService.addPost(postDTO,principal);
        Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Optional<PostDTO> temp = postService.findById(postDTO.getId());
        AssertionsForClassTypes.assertThat(temp.get()).isEqualTo(postDTO);
    }

    @Test
    void shouldThrowUserNotAuthorizedException_addPost() throws IOException {
        //given
        User user3 = new User
                (1, "User 3", "Firstname 3", "Last name 3",
                        "pw3" ,"mail3@mail.com",
                        new Role("ROLE_USER"), new HashSet<>(), new HashSet<>());
        UserDTO userDTO1 = new UserDTO(user3.getId(), user3.getUsername(), user3.getFirstName(),
                user3.getLastName(),user3.getEmail(),user3.getRole());
        Post post = new Post(10,"New heading", "New Text",LocalDateTime.now(), new HashSet<>(), new HashSet<>(),null, user3);
        PostDTO postDTO = new PostDTO(post.getId(), post.getHeading(),post.getText(),new HashSet<>(), post.getDate(),null, "", userDTO1);

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return user3.getUsername();
            }
        };
        //when
        when(postRepository.save(post)).thenReturn(post);
        when(mappingService.postToPostDto(post)).thenReturn(postDTO);
        when(mappingService.newPostDtoToPost(postDTO)).thenReturn(post);
        when(userRepository.findByUsername(principal.getName())).thenReturn(Optional.of(user3));
        given(postRepository.save(post)).willAnswer(invocation -> invocation.getArgument(0));
        //then
        assertThrows(UserNotAuthorizedException.class, () -> postService.addPost(postDTO,principal));
    }
}