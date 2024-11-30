package edu.bowiestateuni.groupproj.foodpantry.dao.repository;

import edu.bowiestateuni.groupproj.foodpantry.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
