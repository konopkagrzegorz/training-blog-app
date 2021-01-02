package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.TagDTO;
import com.training.trainingblogapp.domain.model.Tag;
import com.training.trainingblogapp.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private TagRepository tagRepository;
    private MappingService mappingService;

    @Autowired
    public TagService(TagRepository tagRepository, MappingService mappingService) {
        this.tagRepository = tagRepository;
        this.mappingService = mappingService;
    }

    public List<TagDTO> findAll() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(tag -> {
            TagDTO tagDTO = mappingService.tagDtoToTag(tag);
            return tagDTO;
        }).collect(Collectors.toList());
    }

    public void add(TagDTO tagDTO) {
        Tag tag = mappingService.tagToTagDto(tagDTO);
        tagRepository.save(tag);
    }
}
