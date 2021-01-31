package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturn30Posts() {
        List<Post> postsList = postRepository.findAll();
        assertEquals(30,postsList.size());
    }

    @Test
    public void shouldReturn1Post_findAllPostContainsPhase() {
        List<Post> postsList = postRepository.findAllPostContainsPhase("donec non");
        assertEquals(1,postsList.size());
    }

    @Test
    public void shouldReturn13PostsUpperCase_findAllPostContainsPhase() {
        List<Post> postsList = postRepository.findAllPostContainsPhase("QUAM");
        assertEquals(13,postsList.size());
    }

    @Test
    public void shouldReturn13PostsLowerCase_findAllPostContainsPhase() {
        List<Post> postsList = postRepository.findAllPostContainsPhase("quam");
        assertEquals(13,postsList.size());
    }

    @Test
    public void shouldReturn0Posts_findAllPostContainsPhase() {
        List<Post> postsList = postRepository.findAllPostContainsPhase("xxxxxxx");
        assertEquals(0,postsList.size());
    }

    @Test
    public void shouldReturn4Posts_findByTags_Id() {
        List<Post> postsList = postRepository.findByTags_Id(4L);
        assertEquals(4,postsList.size());
    }

    @Test
    public void shouldReturn0Posts_findByTags_Id() {
        List<Post> postsList = postRepository.findByTags_Id(999L);
        assertEquals(0,postsList.size());
    }
    @Test
    public void shouldReturn23Posts_deleteByUser() {
        postRepository.deleteByUser(userRepository.findById(1L).get());
        List<Post> postList = postRepository.findAll();
        assertEquals(23,postList.size());
    }

    @Test
    public void shouldReturn30Posts_deleteByUser() {
        postRepository.deleteByUser(userRepository.findById(10L).get());
        List<Post> postList = postRepository.findAll();
        assertEquals(30,postList.size());
    }
}