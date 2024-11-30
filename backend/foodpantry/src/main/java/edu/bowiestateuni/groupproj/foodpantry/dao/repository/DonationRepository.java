package edu.bowiestateuni.groupproj.foodpantry.dao.repository;

import edu.bowiestateuni.groupproj.foodpantry.entities.DonationEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<DonationEntity, Long> {
    Page<DonationEntity> findAllByDonatedBy(UserEntity donatedBy, Pageable pageable);

}
