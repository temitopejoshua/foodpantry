package edu.bowiestateuni.groupproj.foodpantry.services.impl;

import edu.bowiestateuni.groupproj.foodpantry.dao.AuditTrailDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.CustomerDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.EmployeeDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.UserDAO;
import edu.bowiestateuni.groupproj.foodpantry.entities.AuditTrailEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.CustomerEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.EmployeeEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.AuditTrailAction;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.RoleTypeConstant;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.UserTypeConstant;
import edu.bowiestateuni.groupproj.foodpantry.services.RegistrationService;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.registration.RegistrationRequestDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.CustomerDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.EmployeeDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.security.AuthenticatedUser;
import edu.bowiestateuni.groupproj.foodpantry.utils.EncryptionUtil;
import edu.bowiestateuni.groupproj.foodpantry.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    @Value("${security.aes.encryption-key}")
    private String key;

    @Value("${security.aes.initialization-vector}")
    private String iv;

    private final UserDAO userDAO;
    private final CustomerDAO customerDAO;
    private final EmployeeDAO employeeDAO;
    private final PasswordEncoder passwordEncoder;
    private final AuditTrailDAO auditTrailDAO;

    public RegistrationServiceImpl(UserDAO userDAO, CustomerDAO customerDAO, EmployeeDAO employeeDAO, PasswordEncoder passwordEncoder, AuditTrailDAO auditTrailDAO) {
        this.userDAO = userDAO;
        this.customerDAO = customerDAO;
        this.employeeDAO = employeeDAO;
        this.passwordEncoder = passwordEncoder;
        this.auditTrailDAO = auditTrailDAO;
    }

    @Override
    public EmployeeDTO registerEmployee(final AuthenticatedUser authenticatedUser,  final RegistrationRequestDTO registrationRequest) {
        log.info("Registering employee with email address: {}", registrationRequest.getEmailAddress());
        validateEmailUniqueness(registrationRequest.getEmailAddress());

        final UserEntity userEntity = createUserEntity(registrationRequest);
        return registerEmployee(authenticatedUser, registrationRequest, userEntity);
    }

    @Override
    public CustomerDTO registerCustomer(RegistrationRequestDTO registrationRequest) {
        validateEmailUniqueness(registrationRequest.getEmailAddress());

        final UserEntity userEntity = createUserEntity(registrationRequest);
        return registerCustomer(registrationRequest, userEntity);
    }

    private void validateEmailUniqueness(String emailAddress) {
        if (userDAO.existsByEmailAddress(emailAddress)) {
            throw new IllegalArgumentException("A user already exists with the email address " + emailAddress);
        }
    }

    private UserEntity createUserEntity(final RegistrationRequestDTO registrationRequest) {
        final String hashedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        return UserEntity.builder()
                .emailAddress(registrationRequest.getEmailAddress())
                .password(hashedPassword)
                .userType(UserTypeConstant.valueOf(registrationRequest.getUserType()))
                .name(registrationRequest.getName())
                .build();
    }

    private CustomerDTO registerCustomer(RegistrationRequestDTO registrationRequest, UserEntity userEntity) {
        final String encryptedPhoneNumber = EncryptionUtil.encryptObject(registrationRequest.getPhoneNumber(), key, iv);

        final CustomerEntity customerEntity = CustomerEntity.builder()
                .user(userEntity)
                .phoneNumber(encryptedPhoneNumber)
                .build();

        customerDAO.save(customerEntity);
        return new CustomerDTO(customerEntity);
    }

    private EmployeeDTO registerEmployee(AuthenticatedUser authenticatedUser, RegistrationRequestDTO registrationRequest, UserEntity userEntity) {
        validateEmployeeFields(registrationRequest);

        final EmployeeEntity employeeEntity = buildEmployeeEntity(registrationRequest, userEntity);

        employeeDAO.save(employeeEntity);
        createAuditTrail(authenticatedUser, employeeEntity);
        return employeeDAO.entityToDTO(employeeEntity);
    }

    private void validateEmployeeFields(RegistrationRequestDTO registrationRequest) {
        if (StringUtils.isBlank(registrationRequest.getAccountNumber())) {
            throw new IllegalArgumentException("Account number is required");
        }
        if (StringUtils.isBlank(registrationRequest.getSsn())) {
            throw new IllegalArgumentException("SSN is required");
        }
        if (StringUtils.isBlank(registrationRequest.getRoutingNumber())) {
            throw new IllegalArgumentException("Routing number is required");
        }
        if (registrationRequest.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Employee date of birth is required");
        }
        if (registrationRequest.getDateJoined() == null) {
            throw new IllegalArgumentException("Employee joined date is required");
        }
        if (registrationRequest.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }
    }

    private EmployeeEntity buildEmployeeEntity(RegistrationRequestDTO registrationRequest, UserEntity userEntity) {
        final String encryptedSSN = EncryptionUtil.encryptObject(registrationRequest.getSsn(), key, iv);
        final String encryptedAccountNumber = EncryptionUtil.encryptObject(registrationRequest.getAccountNumber(), key, iv);
        final String encryptedRoutingNumber = EncryptionUtil.encryptObject(registrationRequest.getRoutingNumber(), key, iv);

        return EmployeeEntity.builder()
                .user(userEntity)
                .dateJoined(registrationRequest.getDateJoined())
                .dateOfBirth(registrationRequest.getDateOfBirth())
                .accountNumber(encryptedAccountNumber)
                .routingNumber(encryptedRoutingNumber)
                .ssn(encryptedSSN)
                .employeeRole(RoleTypeConstant.valueOf(registrationRequest.getRole()))
                .build();
    }
    private void createAuditTrail(final AuthenticatedUser creator, final EmployeeEntity employeeEntity){
        final String comment = String.format("Employee %s created by %s", employeeEntity.getUser().getEmailAddress(), creator.getEmail());
        final AuditTrailEntity auditTrailEntity = AuditTrailEntity.builder()
                .auditTrailAction(AuditTrailAction.EMPLOYEE_CREATION)
                .actorEmail(creator.getEmail())
                .oldState(WebUtils.asJsonString(employeeEntity))
                .currentState(WebUtils.asJsonString(employeeEntity))
                .comment(comment)
                .build();
        auditTrailDAO.save(auditTrailEntity);
    }

}
