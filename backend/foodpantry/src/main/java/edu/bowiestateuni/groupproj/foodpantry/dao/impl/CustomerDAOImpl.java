package edu.bowiestateuni.groupproj.foodpantry.dao.impl;

import edu.bowiestateuni.groupproj.foodpantry.dao.CustomerDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.repository.CustomerRepository;
import edu.bowiestateuni.groupproj.foodpantry.entities.CustomerEntity;
import edu.bowiestateuni.groupproj.foodpantry.services.impl.CrudDAOImpl;
import org.springframework.stereotype.Service;

@Service
public class CustomerDAOImpl extends CrudDAOImpl<CustomerEntity, Long> implements CustomerDAO {
    private final CustomerRepository repository;

    public CustomerDAOImpl(CustomerRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
