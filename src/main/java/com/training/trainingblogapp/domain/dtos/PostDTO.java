package com.training.trainingblogapp.domain.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class PostDTO {

    private long id;
    private String heading;
    private String text;
    private LocalDateTime date;
    private MultipartFile image;
    private String imageEncoded;
    private UserDTO userDTO;

}
