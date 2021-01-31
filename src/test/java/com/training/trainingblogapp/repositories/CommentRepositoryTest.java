package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Comment;
import com.training.trainingblogapp.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnAllComments() {
        List<Comment> commentsList = commentRepository.findAllByOrderByDateDesc();
        assertEquals(100,commentsList.size());
        assertEquals(66, commentsList.get(0).getId());
        assertEquals(78,commentsList.get(99).getId());
    }

    @Test
    public void shouldReturnCommentsByPostId() {
        List<Comment> commentsList = commentRepository.findCommentByPost_IdOrderByDateDesc(1L);
        assertEquals(4,commentsList.size());
        assertEquals(87, commentsList.get(0).getId());
        assertEquals(55, commentsList.get(3).getId());
    }

    @Test
    public void shouldDeleteCommentsByUser() {
        Optional<User> user = userRepository.findById(10L);
        commentRepository.deleteByUser(user.get());
        assertEquals(87, commentRepository.findAllByOrderByDateDesc().size());
    }

    @Test
    public void shouldNotDeleteCommentByUser() {
        Optional<User> user = userRepository.findById(0L);
        assertThrows(NoSuchElementException.class, () -> commentRepository.deleteByUser(user.get()));
    }
}