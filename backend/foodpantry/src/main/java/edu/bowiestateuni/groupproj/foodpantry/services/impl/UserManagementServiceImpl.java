package edu.bowiestateuni.groupproj.foodpantry.services.impl;

import edu.bowiestateuni.groupproj.foodpantry.dao.AuditTrailDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.CustomerDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.EmployeeDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.UserDAO;
import edu.bowiestateuni.groupproj.foodpantry.entities.AuditTrailEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.EmployeeEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.AuditTrailAction;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.EmployeeRoleTypeConstant;
import edu.bowiestateuni.groupproj.foodpantry.services.UserManagementService;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.CustomerDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.EmployeeDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.EmployeeUpdateRequestDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.UserDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.security.AuthenticatedUser;
import edu.bowiestateuni.groupproj.foodpantry.utils.WebUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class UserManagementServiceImpl implements UserManagementService {
    private final EmployeeDAO employeeDAO;
    private final CustomerDAO customerDAO;
    private final AuditTrailDAO auditTrailDAO;
    private final UserDAO userDAO;
    @Override
    public Page<EmployeeDTO> getAllEmployees(AuthenticatedUser authenticatedUser, Pageable pageable) {
        log.info("User - [{}] trying to get all employees", authenticatedUser.getEmail());
        return employeeDAO.findAll(pageable).map(employeeDAO::entityToDTO);
    }

    @Override
    public Page<CustomerDTO> getAllCustomers(AuthenticatedUser authenticatedUser, Pageable pageable) {
        log.info("User - [{}] trying to get all customers", authenticatedUser.getEmail());
        return customerDAO.findAll(pageable).map(CustomerDTO::new);
    }

    @Override
    public Page<UserDTO> getAllUsers(AuthenticatedUser authenticatedUser, Pageable pageable) {
        return userDAO.findAll(pageable).map(UserDTO::new);
    }

    @Override
    public EmployeeDTO changeEmployeeRole(AuthenticatedUser authenticatedUser, EmployeeUpdateRequestDTO requestDTO) {
        final EmployeeEntity employee = employeeDAO.findById(requestDTO.getEmployeeId()).orElseThrow(() -> new IllegalArgumentException("No Employee found with the specified id " + requestDTO.getEmployeeId()));
        final EmployeeRoleTypeConstant role = EmployeeRoleTypeConstant.valueOf(requestDTO.getRole());
        log.info("User - [{}] trying to change role of employee - [{}] to [{}]", authenticatedUser.getEmail(), employee.getUser().getEmailAddress(), requestDTO.getRole());
        final String comment = String.format("Employee role changed from [%s] to [%s]", employee.getEmployeeRole(), requestDTO.getRole());
        final AuditTrailEntity auditTrailEntity = createAuditTrail(authenticatedUser.getEmail(), employee, comment);
        employee.setEmployeeRole(role);
        employeeDAO.save(employee);
        auditTrailEntity.setCurrentState(WebUtils.asJsonString(employee));
        auditTrailDAO.save(auditTrailEntity);
        return employeeDAO.entityToDTO(employee);
    }
    private AuditTrailEntity createAuditTrail(String actorEmail, EmployeeEntity employee, String comment) {
        return AuditTrailEntity.builder()
                .auditTrailAction(AuditTrailAction.UPDATE_EMPLOYEE_ROLE)
                .actorEmail(actorEmail)
                .comment(comment)
                .oldState(WebUtils.asJsonString(employee))
                .build();
    }

}
