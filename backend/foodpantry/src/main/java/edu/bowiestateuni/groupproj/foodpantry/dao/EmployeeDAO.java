package edu.bowiestateuni.groupproj.foodpantry.dao;

import edu.bowiestateuni.groupproj.foodpantry.entities.EmployeeEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.EmployeeDTO;

import java.util.Optional;

public interface EmployeeDAO extends CrudDAO<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByUser(final UserEntity user);
    EmployeeDTO entityToDTO(EmployeeEntity employeeEntity);
}
