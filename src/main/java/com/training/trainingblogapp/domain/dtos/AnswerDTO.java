package com.training.trainingblogapp.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    @Size(min = 3,max = 999,message = "Test must be between {min} and {max} characters")
    private String text;
}
