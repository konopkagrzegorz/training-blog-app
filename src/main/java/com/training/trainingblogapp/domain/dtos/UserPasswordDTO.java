package com.training.trainingblogapp.domain.dtos;

import com.training.trainingblogapp.validation.FieldsValueMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldsValueMatch(field = "password", fieldMatch = "confirmPassword")
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDTO {

    private long id;

    @NotBlank(message = "Space is not allowed")
    @Size(min = 3,max = 20,message = "Password must be between {min} - {max} characters")
    private String password;

    private String confirmPassword;

}
