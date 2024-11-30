package edu.bowiestateuni.groupproj.foodpantry.services.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;



@Data
public class LoginRequestDTO {
    @NotBlank(message = "Email address is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String emailAddress;
    @NotBlank(message = "Password is required")
    private String password;
}
