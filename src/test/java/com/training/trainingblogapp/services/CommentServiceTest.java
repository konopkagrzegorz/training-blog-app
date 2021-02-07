package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.CommentDTO;
import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.domain.model.Comment;
import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.repositories.CommentRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.assertj.core.util.Lists;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class CommentServiceTest {

    private Post post1 = new Post(1, null,null,null,null,null,null,null);
    private Post post2 = new Post(2, null,null,null,null,null,null,null);
    private PostDTO postDTO = new PostDTO(post1.getId(),null,null,null,null,null,null,null);
    private Comment comment1 = new Comment(1,null,LocalDateTime.of(1991,5,20,19,30),null,post1);
    private Comment comment2 = new Comment(2,null,LocalDateTime.of(1993,5,20,19,30),null,post1);
    private CommentDTO commentDTO1 = new CommentDTO(comment1.getId(),null,comment1.getDate(),null, postDTO);
    private CommentDTO commentDTO2 = new CommentDTO(comment2.getId(),null,comment2.getDate(),null, postDTO);
    private User user = new User(1,"null",null,null,null,null,null,null,new HashSet<>());

    @Mock
    private UserRepository userRepository;

    @Mock
    private MappingService mappingService;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllComments_findAll() {
        //given
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setText("Text 1");
        comment1.setDate(LocalDateTime.of(1991,3,20,19,20));
        Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setText("Text 2");
        comment2.setDate(LocalDateTime.now());
        Comment comment3 = new Comment();
        comment3.setId(3);
        comment3.setText("Text 3");
        comment3.setDate(LocalDateTime.of(1998,3,20,19,20));
        //when
        CommentDTO commentDTO1 = new CommentDTO();
        commentDTO1.setId(comment1.getId());
        commentDTO1.setText(comment1.getText());
        commentDTO1.setDate(comment1.getDate());
        CommentDTO commentDTO2 = new CommentDTO();
        commentDTO2.setId(comment2.getId());
        commentDTO2.setText(comment2.getText());
        commentDTO2.setDate(comment2.getDate());
        CommentDTO commentDTO3 = new CommentDTO();
        commentDTO3.setId(comment3.getId());
        commentDTO3.setText(comment3.getText());
        commentDTO3.setDate(comment3.getDate());

        Mockito.when(mappingService.commentToCommentDto(comment1)).thenReturn(commentDTO1);
        Mockito.when(mappingService.commentToCommentDto(comment2)).thenReturn(commentDTO2);
        Mockito.when(mappingService.commentToCommentDto(comment3)).thenReturn(commentDTO3);
        Mockito.when(commentRepository.findAllByOrderByDateDesc()).thenReturn(Lists.list(comment2,comment3,comment1));

        //then
        List<CommentDTO> actual = commentService.findAll();
        assertThat(actual).containsExactly(commentDTO2,commentDTO3,commentDTO1);
    }

    @Test
    void shouldReturn2Comments_findAllByPostId() {
        //when
        Mockito.when(mappingService.commentToCommentDto(comment1)).thenReturn(commentDTO1);
        Mockito.when(mappingService.commentToCommentDto(comment2)).thenReturn(commentDTO2);
        Mockito.when(commentRepository.findCommentByPost_IdOrderByDateDesc(post1.getId())).thenReturn(Lists.list(comment2,comment1));

        //then
        List<CommentDTO> actual = commentService.findAllByPostId(post1.getId());
        assertThat(actual).containsExactly(commentDTO2,commentDTO1);
    }

    @Test
    void shouldReturn0Comments_findAllByPostId() {
        //when
        Mockito.when(mappingService.commentToCommentDto(comment1)).thenReturn(commentDTO1);
        Mockito.when(mappingService.commentToCommentDto(comment2)).thenReturn(commentDTO2);
        Mockito.when(commentRepository.findCommentByPost_IdOrderByDateDesc(post2.getId())).thenReturn(Lists.emptyList());

        //then
        List<CommentDTO> actual = commentService.findAllByPostId(post2.getId());
        assertThat(actual).isEmpty();
    }

    @Test
    void findById() {
        //when
        Mockito.when(mappingService.commentToCommentDto(comment1)).thenReturn(commentDTO1);
        Mockito.when(commentRepository.findById(comment1.getId())).thenReturn(Optional.of(comment1));

        //then
        List<CommentDTO> actual = commentService.findAllByPostId(post2.getId());
        assertThat(actual).isEmpty();
    }

    @Test
    @Disabled
    void saveComment() {
        //given
    }

    @Test
    void deleteById() {
        long id = 1L;
        commentService.deleteById(id);
        verify(commentRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteByUser() {
        //given
        comment1.setUser(user);
        comment2.setUser(user);
        //when
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        //then
        commentService.deleteByUser(user.getUsername());
        verify(commentRepository,times(1)).deleteByUser(user);
    }
}