package edu.bowiestateuni.groupproj.foodpantry.dao.impl;

import edu.bowiestateuni.groupproj.foodpantry.dao.UserDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.repository.UserRepository;
import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;
import edu.bowiestateuni.groupproj.foodpantry.services.impl.CrudDAOImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDAOImpl extends CrudDAOImpl<UserEntity, Long> implements UserDAO {
    private final UserRepository repository;

    public UserDAOImpl(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }
    @Override
    public boolean existsByEmailAddress(String emailAddress) {
        return repository.existsByEmailAddress(emailAddress);
    }
    @Override
    public Optional<UserEntity> findByEmailAddress(String emailAddress) {
        return repository.findByEmailAddress(emailAddress);
    }
}
