package edu.bowiestateuni.groupproj.foodpantry.services.dto.donation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DonationRequest {
    @NotNull
    @Pattern(regexp = "(RICE|BEANS|POTATOES|CHICKEN|BREAD|CEREAL)", message = "Allowed values for foodType are: RICE|BEANS|POTATOES|CHICKEN|BREAD|CEREAL")
    private String foodType;
    @Min(value = 1, message = "Minimum donation quantity is 1")
    private Integer quantity;
}
