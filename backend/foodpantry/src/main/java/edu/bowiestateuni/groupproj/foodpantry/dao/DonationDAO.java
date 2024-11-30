package edu.bowiestateuni.groupproj.foodpantry.dao;

import edu.bowiestateuni.groupproj.foodpantry.entities.DonationEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DonationDAO extends CrudDAO<DonationEntity, Long> {
    Page<DonationEntity> findAllByDonatedBy(UserEntity donatedBy, Pageable pageable);
}
