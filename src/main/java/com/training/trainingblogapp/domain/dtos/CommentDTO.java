package com.training.trainingblogapp.domain.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private long id;

    private String text;
    private LocalDateTime date;
    private UserDTO userDTO;
    private PostDTO postDTO;
}
