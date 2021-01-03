package com.training.trainingblogapp.domain.dtos;

import lombok.Data;
import javax.validation.constraints.Size;

@Data
public class AnswerDTO {

    @Size(min = 3,max = 999,message = "Test must be between {min} and {max} characters")
    private String text;
}
