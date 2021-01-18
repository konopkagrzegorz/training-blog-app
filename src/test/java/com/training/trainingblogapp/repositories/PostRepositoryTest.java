package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void shouldReturn30Posts() {
        List<Post> postsList = postRepository.findAll();
        assertEquals(30,postsList.size());
    }

}