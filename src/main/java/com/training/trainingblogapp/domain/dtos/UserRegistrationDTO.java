package com.training.trainingblogapp.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode
public class UserRegistrationDTO {

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

    @NotBlank(message = "Space is not allowed")
    @Size(min = 3,max = 20,message = "Password must be between {min} - {max} characters")
    private String password;

    @NotBlank(message = "Space is not allowed")
    @Size(min = 3,max = 20,message = "Password must be between {min} - {max} characters")
    private String confirmPassword;

    @AssertTrue(message = "Please accept terms of use")
    private boolean terms;
}
