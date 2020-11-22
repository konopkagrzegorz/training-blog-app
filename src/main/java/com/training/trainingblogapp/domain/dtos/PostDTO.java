package com.training.trainingblogapp.domain.dtos;

import com.training.trainingblogapp.domain.model.User;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostDTO {

    private long id;
    private String heading;
    private String text;
    private LocalDateTime date;
    private UserDTO userDTO;
}
