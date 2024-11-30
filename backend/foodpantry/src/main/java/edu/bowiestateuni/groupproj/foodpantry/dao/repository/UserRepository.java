package edu.bowiestateuni.groupproj.foodpantry.dao.repository;

import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailAddress(String emailAddress);
    boolean existsByEmailAddress(String emailAddress);
}
