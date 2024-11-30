package edu.bowiestateuni.groupproj.foodpantry.dao.impl;

import edu.bowiestateuni.groupproj.foodpantry.dao.EmployeeDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.repository.EmployeeRepository;
import edu.bowiestateuni.groupproj.foodpantry.entities.EmployeeEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.EmployeeDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.UserDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.impl.CrudDAOImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeDAOImpl extends CrudDAOImpl<EmployeeEntity, Long> implements EmployeeDAO {
    @Value("${security.aes.encryption-key}")
    private String key;

    @Value("${security.aes.initialization-vector}")
    private String iv;

   private final EmployeeRepository repository;

    public EmployeeDAOImpl(EmployeeRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Optional<EmployeeEntity> findByUser(final UserEntity user) {
        return repository.findByUser(user);
    }

    @Override
    public EmployeeDTO entityToDTO(EmployeeEntity employeeEntity) {
        return EmployeeDTO.builder()
                .id(employeeEntity.getId())
                .user(new UserDTO(employeeEntity.getUser()))
                .role(employeeEntity.getEmployeeRole())
                .dateOfBirth(employeeEntity.getDateOfBirth())
                .routingNumber(employeeEntity.getRoutingNumber())
                .accountNumber(employeeEntity.getAccountNumber())
                .build();
    }
}
