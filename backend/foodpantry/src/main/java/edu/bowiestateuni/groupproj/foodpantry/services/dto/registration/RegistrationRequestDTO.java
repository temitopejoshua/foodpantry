package edu.bowiestateuni.groupproj.foodpantry.services.dto.registration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrationRequestDTO {
    @NotBlank(message = "Please provide a valid email address")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String emailAddress;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#\\$%\\^&\\*\\(\\)_\\+\\-=\\[\\]\\{\\};:'\"\\\\|,.<>\\/\\?`~]).{6,20}$",
            message = "Password must be 6-20 characters long, contain at least one letter and one number, and a special character"
    )
    private String password;
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z\\s-]{3,30}$", message = "The name you entered does not look valid")
    private String name;
    @NotNull(message = "User type is required ")
    @Pattern(regexp = "(CUSTOMER|EMPLOYEE)", message = "Allowed values for usertype: CUSTOMER,EMPLOYEE")
    private String userType;
    @Pattern(regexp = "(CUSTOMER|FRONT_DESK|STAFF|CEO)", message = "Allowed values for role: CUSTOMER,FRONT_DESK,STAFF,CEO")
    private String role;
    private LocalDate dateJoined;
    private LocalDate dateOfBirth;
    @Pattern(regexp = "^\\d{10}$", message = "Phone number can only be 10 to 11 digits, format 9195333333")
    private String phoneNumber;
    @Pattern(regexp = "^(?!000|666|9\\d\\d)(\\d{3})-(?!00)(\\d{2})-(?!0000)(\\d{4})$|^(?!000|666|9\\d\\d)(\\d{3})(?!00)(\\d{2})(?!0000)(\\d{4})$", message = "Invalid SSN format: Sample XXX-XX-XXXX")
    private String ssn;
    @Pattern(regexp = "^\\d{8,12}$", message = "Account number can only be between 8 - 10 digits")
    private String accountNumber;
    @Pattern(regexp = "^\\d{9}$", message = "Routing number can only be between 9 digits")
    private String routingNumber;
}

