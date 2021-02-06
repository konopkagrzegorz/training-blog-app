package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.TagDTO;
import com.training.trainingblogapp.domain.model.Tag;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.exceptions.UserNotAuthorizedException;
import com.training.trainingblogapp.repositories.TagRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService {

    private TagRepository tagRepository;
    private MappingService mappingService;
    private UserRepository userRepository;

    @Autowired
    public TagService(TagRepository tagRepository, MappingService mappingService, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.mappingService = mappingService;
        this.userRepository = userRepository;
    }

    public List<TagDTO> findAll() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(tag -> {
            TagDTO tagDTO = mappingService.tagToTagDto(tag);
            return tagDTO;
        }).collect(Collectors.toList());
    }

    public void add(TagDTO tagDTO) {
        Tag tag = mappingService.tagDtoToTag(tagDTO);
        tagRepository.save(tag);
    }

    public Optional<TagDTO> findByName(String name) {
        Optional<Tag> tag = Optional.ofNullable(tagRepository.findByNameIgnoreCase(name)).get();
        Optional<TagDTO> tagDTO = Optional.empty();
        if (tag.isPresent()) {
            tagDTO = Optional.ofNullable(mappingService.tagToTagDto(tag.get()));
        }
        return tagDTO;
    }

    public Optional<TagDTO> findById(long id) {
        Optional<Tag> tag = Optional.ofNullable(tagRepository.findById(id)).get();
        Optional<TagDTO> tagDTO = Optional.empty();
        if (tag.isPresent()) {
            tagDTO = Optional.ofNullable(mappingService.tagToTagDto(tag.get()));
        }
        return tagDTO;
    }
    public List<TagDTO> findAllByPostId(Long id) {
        List<Tag> tags = tagRepository.findByPosts_Id(id);
        List<TagDTO> tagsDTO = tags.stream().map(tag -> {
            TagDTO tagDTO = mappingService.tagToTagDto(tag);
            return tagDTO;
        }).collect(Collectors.toList());
        return tagsDTO;
    }


    public void deleteById(long id, Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        if (user.isPresent() && user.get().getRole().getName().equals("ROLE_ADMIN")) {
            tagRepository.deleteById(id);
        } else {
            throw new UserNotAuthorizedException("You are not logged in or You are not an admin!");
        }
    }
}
