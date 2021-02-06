package com.training.trainingblogapp.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private long id;

    @Size(min = 3,max = 80,message = "Heading must be between {min} and {max} characters")
    private String heading;

    @Size(min = 3,max = 25000,message = "Text must be between {min} and {max} characters")
    private String text;

    private Set<TagDTO> tags;

    private LocalDateTime date;
    private MultipartFile image;
    private String imageEncoded;
    private UserDTO userDTO;

}
