package edu.bowiestateuni.groupproj.foodpantry.services;

import edu.bowiestateuni.groupproj.foodpantry.services.dto.registration.RegistrationRequestDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.CustomerDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.EmployeeDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.security.AuthenticatedUser;

public interface RegistrationService {
    EmployeeDTO registerEmployee(final AuthenticatedUser currentUser, final RegistrationRequestDTO registrationRequest);
    CustomerDTO registerCustomer(final RegistrationRequestDTO registrationRequest);
}
