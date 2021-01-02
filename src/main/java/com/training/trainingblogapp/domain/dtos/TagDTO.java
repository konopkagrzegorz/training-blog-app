package com.training.trainingblogapp.domain.dtos;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class TagDTO {

    private long id;

    @Size(min = 3,max = 25000,message = "Text must be between {min} and {max} characters")
    private String name;
}
