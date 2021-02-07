package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.TagDTO;
import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.domain.model.Tag;
import com.training.trainingblogapp.repositories.TagRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class TagServiceTest {
    private Tag tag1 = new Tag(1, "Tag 1", new HashSet<>());
    private Tag tag2 = new Tag(2, "Tag 1", new HashSet<>());
    private Tag tag3 = new Tag(3, "Tag 1", new HashSet<>());
    private TagDTO tagDTO1 = new TagDTO(tag1.getId(), tag1.getName(), new HashSet<>());
    private TagDTO tagDTO2 = new TagDTO(tag2.getId(), tag2.getName(), new HashSet<>());
    private TagDTO tagDTO3 = new TagDTO(tag3.getId(), tag3.getName(), new HashSet<>());

    private Tag tag = new Tag(1, "Tag 1", new HashSet<>());
    private TagDTO tagDTO = new TagDTO(tag.getId(),tag.getName(),null);

    @Mock
    private MappingService mappingService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        //given
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        Tag tag3 = new Tag();
        TagDTO tagDTO1 = new TagDTO();
        TagDTO tagDTO2 = new TagDTO();
        TagDTO tagDTO3 = new TagDTO();
        //when
        Mockito.when(tagRepository.findAll()).thenReturn(Lists.list(tag1,tag2,tag3));
        Mockito.when(mappingService.tagToTagDto(tag1)).thenReturn(tagDTO1);
        Mockito.when(mappingService.tagToTagDto(tag2)).thenReturn(tagDTO2);
        Mockito.when(mappingService.tagToTagDto(tag3)).thenReturn(tagDTO3);
        //then
        List<TagDTO> actual = tagService.findAll();
        assertThat(actual).containsExactlyInAnyOrder(tagDTO1,tagDTO2,tagDTO3);
    }

    @Test
    @Disabled
    void add() {
    }

    @Test
    void shouldReturnTag_findByName() {
        //when
        Mockito.when(tagRepository.findByNameIgnoreCase(tag.getName())).thenReturn(Optional.of(tag));
        Mockito.when(mappingService.tagToTagDto(tag)).thenReturn(tagDTO);

        //then
        Optional<TagDTO> actual = tagService.findByName(tag.getName());
        assertThat(actual.get()).isEqualTo(tagDTO);

    }

    @Test
    void shouldReturnEmptyTag_findByName() {
        //when
        Mockito.when(tagRepository.findByNameIgnoreCase(tag.getName())).thenReturn((Optional.empty()));

        //then
        Optional<TagDTO> actual = tagService.findByName(tag.getName());
        assertThat(actual).isEmpty();
    }

    @Test
    void findById() {
        //when
        Mockito.when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
        Mockito.when(mappingService.tagToTagDto(tag)).thenReturn(tagDTO);

        //then
        Optional<TagDTO> actual = tagService.findById(tag.getId());
        assertThat(actual.get()).isEqualTo(tagDTO);
    }

    @Test
    void findAllByPostId() {
        //given
        Post post = new Post();
        post.setId(1);

        tag1.getPosts().add(post);
        tag2.getPosts().add(post);
        tag3.getPosts().add(post);

        //when
        Mockito.when(tagRepository.findByPosts_Id(post.getId())).thenReturn(Lists.list(tag1,tag2,tag3));
        Mockito.when(mappingService.tagToTagDto(tag1)).thenReturn(tagDTO1);
        Mockito.when(mappingService.tagToTagDto(tag2)).thenReturn(tagDTO2);
        Mockito.when(mappingService.tagToTagDto(tag3)).thenReturn(tagDTO3);

        //then
        List<TagDTO> actual = tagService.findAllByPostId(post.getId());
        assertThat(actual).containsExactlyInAnyOrder(tagDTO1,tagDTO2,tagDTO3);
    }

    @Test
    @Disabled
    void deleteById() {
        long id = 1L;
        //tagService.deleteById(id);
        verify(tagRepository, times(1)).deleteById(id);
    }
}