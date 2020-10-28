package com.training.trainingblogapp.domain.dtos;

import com.training.trainingblogapp.validation.FieldsValueMatch;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode
@FieldsValueMatch(field = "password", fieldMatch = "confirmPassword")
public class UserPasswordDTO {

    @NotBlank(message = "Space is not allowed")
    @Size(min = 3,max = 20,message = "Password must be between {min} - {max} characters")
    private String password;

    private String confirmPassword;

}
