package edu.bowiestateuni.groupproj.foodpantry.services;

import edu.bowiestateuni.groupproj.foodpantry.services.dto.donation.DonationRequest;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.donation.DonationResponse;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.donation.UpdateDonationRequest;
import edu.bowiestateuni.groupproj.foodpantry.services.security.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DonationService {
    DonationResponse donateFood(final AuthenticatedUser currentUser, final DonationRequest donationRequest);
    DonationResponse updateDonationStatus(final AuthenticatedUser currentUser, final UpdateDonationRequest updateDonationRequest);
    Page<DonationResponse> fetchDonations(final AuthenticatedUser currentUser, final Pageable page);
}
