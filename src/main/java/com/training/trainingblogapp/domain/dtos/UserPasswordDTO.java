package com.training.trainingblogapp.domain.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode
public class UserPasswordDTO {

    @NotBlank(message = "Space is not allowed")
    @Size(min = 3,max = 20,message = "Password must be between {min} - {max} characters")
    private String password;

    @NotBlank(message = "Space is not allowed")
    @Size(min = 3,max = 20,message = "Password must be between {min} - {max} characters")
    private String confirmPassword;

}
