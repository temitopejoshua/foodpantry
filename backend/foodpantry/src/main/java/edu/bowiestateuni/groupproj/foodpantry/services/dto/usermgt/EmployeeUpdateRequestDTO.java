package edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmployeeUpdateRequestDTO {
    @Min(1)
    private long employeeId;
    @NotBlank
    @Pattern(regexp = "(CUSTOMER|FRONT_DESK|STAFF|CEO)", message = "Allowed values for role: CUSTOMER,FRONT_DESK,STAFF,CEO")
    private String role;
}
