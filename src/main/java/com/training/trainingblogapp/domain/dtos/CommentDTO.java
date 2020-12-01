package com.training.trainingblogapp.domain.dtos;

import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private long id;

    @Size(min = 3,max = 255,message = "Comment must be between {min} and {max} characters")
    private String text;

    private LocalDateTime date;
    private UserDTO userDTO;
    private PostDTO postDTO;
}
