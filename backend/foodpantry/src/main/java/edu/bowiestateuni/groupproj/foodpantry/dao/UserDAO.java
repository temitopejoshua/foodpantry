package edu.bowiestateuni.groupproj.foodpantry.dao;

import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;

import java.util.Optional;

public interface UserDAO extends CrudDAO<UserEntity, Long> {
    Optional<UserEntity> findByEmailAddress(String emailAddress);
    boolean existsByEmailAddress(String emailAddress);
}
