package com.training.trainingblogapp.domain.dtos;

import com.training.trainingblogapp.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long id;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Special characters are not allowed")
    @Size(min = 3, max = 10,message = "Username must be between {min} - {max} characters")
    private String username;

    @NotBlank(message = "Space is not allowed")
    @Size(min = 3,max = 30, message = "First name must be between {min} - {max} characters")
    private String firstName;

    @NotBlank(message = "Space is not allowed")
    @Size(min = 3,max = 30, message = "First name must be between {min} - {max} characters")
    private String lastName;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Must be a valid email")
    private String email;

    private Role role;
}
