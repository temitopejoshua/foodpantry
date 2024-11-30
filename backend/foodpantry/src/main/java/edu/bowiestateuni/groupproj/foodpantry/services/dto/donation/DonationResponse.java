package edu.bowiestateuni.groupproj.foodpantry.services.dto.donation;

import edu.bowiestateuni.groupproj.foodpantry.entities.constant.DonationStatusTypeConstant;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.FoodTypeConstant;

public record DonationResponse(long id, DonationStatusTypeConstant status, int quantity, FoodTypeConstant foodType, String donatedBy, String dateDonated) {
}
