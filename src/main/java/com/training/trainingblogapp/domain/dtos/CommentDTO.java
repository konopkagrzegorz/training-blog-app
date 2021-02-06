package com.training.trainingblogapp.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private long id;

    @Size(min = 3,max = 255,message = "Comment must be between {min} and {max} characters")
    private String text;

    private LocalDateTime date;
    private UserDTO userDTO;
    private PostDTO postDTO;
}
