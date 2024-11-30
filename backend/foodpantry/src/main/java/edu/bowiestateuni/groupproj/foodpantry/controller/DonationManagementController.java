package edu.bowiestateuni.groupproj.foodpantry.controller;

import edu.bowiestateuni.groupproj.foodpantry.services.DonationService;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.donation.DonationRequest;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.donation.DonationResponse;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.donation.UpdateDonationRequest;
import edu.bowiestateuni.groupproj.foodpantry.services.security.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class DonationManagementController {
    private final DonationService donationService;

    @GetMapping(value = "/getUser")
    @Secured(value = {"FRONT_DESK", "STAFF", "CEO", "CUSTOMER"})
    public ResponseEntity<AuthenticatedUser> getCurrentUserDetails(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(authenticatedUser);
    }
    @Secured(value = {"FRONT_DESK", "CUSTOMER"})
    @PutMapping(value = "/donations/donate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DonationResponse> donateFood(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, final @Valid @RequestBody DonationRequest request) {
        final DonationResponse responseDTO = donationService.donateFood(authenticatedUser, request);
        return ResponseEntity.ok(responseDTO);
    }
    @Secured(value = {"FRONT_DESK", "STAFF", "CEO"})
    @PutMapping(value = "/donations/update-status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DonationResponse> updateDonationStatus(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, final @Valid @RequestBody UpdateDonationRequest request) {
        final DonationResponse responseDTO = donationService.updateDonationStatus(authenticatedUser, request);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping(value = "/donations")
    @Secured(value = {"FRONT_DESK", "STAFF", "CEO", "CUSTOMER"})
    public ResponseEntity<Page<DonationResponse>> getDonations(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, final Pageable pageable) {
        final Page<DonationResponse> responseDTO = donationService.fetchDonations(authenticatedUser, pageable);
        return ResponseEntity.ok(responseDTO);
    }
}
