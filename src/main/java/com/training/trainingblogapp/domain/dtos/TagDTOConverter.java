package com.training.trainingblogapp.domain.dtos;


import com.training.trainingblogapp.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagDTOConverter implements Converter<String, TagDTO> {

    private TagService tagService;

    @Autowired
    public TagDTOConverter(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public TagDTO convert(String idString) {
        long id = Long.parseLong(idString);
        return tagService.findById(id).get();
    }
}
