package edu.bowiestateuni.groupproj.foodpantry.services;

import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.CustomerDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.EmployeeDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.EmployeeUpdateRequestDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.UserDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.security.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserManagementService {
    Page<EmployeeDTO> getAllEmployees(AuthenticatedUser authenticatedUser, Pageable pageable);
    Page<CustomerDTO> getAllCustomers(AuthenticatedUser authenticatedUser, Pageable pageable);
    Page<UserDTO> getAllUsers(AuthenticatedUser authenticatedUser, Pageable pageable);
    EmployeeDTO changeEmployeeRole(AuthenticatedUser authenticatedUser, EmployeeUpdateRequestDTO requestDTO);
}
