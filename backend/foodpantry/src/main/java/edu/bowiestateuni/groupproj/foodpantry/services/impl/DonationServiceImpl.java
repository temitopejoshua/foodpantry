package edu.bowiestateuni.groupproj.foodpantry.services.impl;

import edu.bowiestateuni.groupproj.foodpantry.dao.AuditTrailDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.DonationDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.UserDAO;
import edu.bowiestateuni.groupproj.foodpantry.entities.AuditTrailEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.DonationEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.AuditTrailAction;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.DonationStatusTypeConstant;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.FoodTypeConstant;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.UserTypeConstant;
import edu.bowiestateuni.groupproj.foodpantry.services.DonationService;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.donation.DonationRequest;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.donation.DonationResponse;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.donation.UpdateDonationRequest;
import edu.bowiestateuni.groupproj.foodpantry.services.security.AuthenticatedUser;
import edu.bowiestateuni.groupproj.foodpantry.utils.WebUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
@Slf4j
public class DonationServiceImpl implements DonationService {
    private final UserDAO userDAO;
    private final DonationDAO donationDAO;
    private final AuditTrailDAO auditTrailDAO;

    @Override
    public DonationResponse donateFood(final AuthenticatedUser currentUser, final DonationRequest donationRequest) {
        log.info("Attempting to donate food. User: {}, DonationRequest: {}", currentUser.getEmail(), donationRequest);

        final UserEntity donor = userDAO.findByEmailAddress(currentUser.getEmail())
                .orElseThrow(() -> {
                    log.error("Unauthorized user: {}", currentUser.getEmail());
                    return new IllegalArgumentException("Unauthorized user");
                });

        final DonationEntity donationEntity = DonationEntity.builder()
                .donatedBy(donor)
                .quantityDonated(donationRequest.getQuantity())
                .foodType(FoodTypeConstant.valueOf(donationRequest.getFoodType()))
                .build();

        donationDAO.save(donationEntity);
        log.info("Donation saved. DonationEntity: {}", donationEntity);

        return entityToResponse(donationEntity);
    }

    @Override
    public DonationResponse updateDonationStatus(final AuthenticatedUser currentUser, final UpdateDonationRequest request) {
        log.info("Attempting to update donation status. User: {}, UpdateDonationRequest: {}", currentUser.getEmail(), request);
        final DonationStatusTypeConstant donationStatus = DonationStatusTypeConstant.valueOf(request.getDonationStatus());
        final DonationEntity donationEntity = donationDAO.findById(request.getDonationId())
                .orElseThrow(() -> {
                    log.error("No donation found with id: {}", request.getDonationId());
                    return new IllegalArgumentException("No donation found with the specified id");
                });

        if (donationStatus == DonationStatusTypeConstant.PENDING) {
            log.warn("Invalid status update attempt to PENDING for donation id: {}, by user {}", request.getDonationId(), currentUser.getEmail());
            throw new IllegalArgumentException("You can't update donation status to PENDING");
        }
        final String comment = String.format("Donation status updated to %s by %s", donationStatus, currentUser.getEmail());
        final AuditTrailEntity auditTrailEntity = createAuditTrail(currentUser.getEmail(), donationEntity, comment);
        donationEntity.setStatus(donationStatus);
        donationDAO.save(donationEntity);
        auditTrailEntity.setCurrentState(WebUtils.asJsonString(donationEntity));
        auditTrailDAO.save(auditTrailEntity);
        log.info("Donation status updated. DonationEntity: {}", donationEntity);

        return entityToResponse(donationEntity);
    }

    @Override
    public Page<DonationResponse> fetchDonations(final AuthenticatedUser currentUser, final Pageable page) {
        log.info("Fetching donations. User: {}, PageRequest: {}", currentUser.getEmail(), page);
        final UserEntity user = userDAO.findByEmailAddress(currentUser.getEmail())
                .orElseThrow(() -> {
                    log.error("Unauthorized user: {}", currentUser.getEmail());
                    return new IllegalArgumentException("Unauthorized user");
                });
        Page<DonationResponse> donations;
        if (user.getUserType() == UserTypeConstant.CUSTOMER) {
            log.info("User is a CUSTOMER. Fetching donations for user: {}", user.getEmailAddress());
            donations = donationDAO.findAllByDonatedBy(user, page).map(this::entityToResponse);
        } else {
            log.info("User is not a CUSTOMER. Fetching all donations.");
            donations = donationDAO.findAll(page).map(this::entityToResponse);
        }

        log.info("Fetched {} donations for user: {}", donations.getTotalElements(), currentUser.getEmail());
        return donations;
    }

    private AuditTrailEntity createAuditTrail(String actorEmail, DonationEntity donation, String comment) {
        return AuditTrailEntity.builder()
                .auditTrailAction(AuditTrailAction.UPDATE_DONATION_STATUS)
                .actorEmail(actorEmail)
                .comment(comment)
                .oldState(WebUtils.asJsonString(donation))
                .build();
    }

    private DonationResponse entityToResponse(final DonationEntity donationEntity) {
        return new DonationResponse(
                donationEntity.getId(),
                donationEntity.getStatus(),
                donationEntity.getQuantityDonated(),
                donationEntity.getFoodType(),
                donationEntity.getDonatedBy().getName(),
                donationEntity.getDateCreated().format(DateTimeFormatter.ofPattern("EEE, MMM d yyyy h:mm a"))
        );
    }
}
