package com.training.trainingblogapp.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private long id;

    @Size(min = 3,max = 30,message = "Name must be between {min} and {max} characters")
    private String name;

    @Size(min = 3,max = 80,message = "Subject must be between {min} and {max} characters")
    private String subject;

    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Must be a valid email")
    private String contactEmail;

    @Size(min = 3,max = 300,message = "Message must be between {min} and {max} characters")
    private String text;

}
