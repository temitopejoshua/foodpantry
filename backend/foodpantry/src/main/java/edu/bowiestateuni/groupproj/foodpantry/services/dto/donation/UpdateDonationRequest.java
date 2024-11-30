package edu.bowiestateuni.groupproj.foodpantry.services.dto.donation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateDonationRequest {
    private long donationId;
    @NotNull(message = "donationStatus is required")
    @Pattern(regexp = "(APPROVED|REJECTED|DISTRIBUTED)", message = "Allowed values for donationStatus are: APPROVED|REJECTED|DISTRIBUTED")
    private String donationStatus;
}
