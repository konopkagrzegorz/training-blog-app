package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void shouldReturn9Tags_findAll() {
        List<Tag> tagsList = tagRepository.findAll();
        assertEquals(9,tagsList.size());
    }

    @Test
    public void shouldReturn1Tag_findByNameIgnoreCase1() {
        Optional<Tag> tag = tagRepository.findByNameIgnoreCase("others");
        assertEquals(true, tag.isPresent());
    }

    @Test
    public void shouldReturn1Tag_findByNameIgnoreCase2() {
        Optional<Tag> tag = tagRepository.findByNameIgnoreCase("oThErs");
        assertEquals(true, tag.isPresent());
    }

    @Test
    public void shouldNotReturnTag_findByNameIgnoreCase() {
        Optional<Tag> tag = tagRepository.findByNameIgnoreCase("xxx");
        assertEquals(false, tag.isPresent());
    }
}