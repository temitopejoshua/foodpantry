package edu.bowiestateuni.groupproj.foodpantry.dao.impl;

import edu.bowiestateuni.groupproj.foodpantry.dao.DonationDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.repository.DonationRepository;
import edu.bowiestateuni.groupproj.foodpantry.entities.DonationEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;
import edu.bowiestateuni.groupproj.foodpantry.services.impl.CrudDAOImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DonationDAOImpl extends CrudDAOImpl<DonationEntity, Long> implements DonationDAO {
   private final DonationRepository repository;

    public DonationDAOImpl(DonationRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Page<DonationEntity> findAllByDonatedBy(UserEntity donatedBy, Pageable pageable) {
        return repository.findAllByDonatedBy(donatedBy, pageable);
    }
}
