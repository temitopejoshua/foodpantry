package edu.bowiestateuni.groupproj.foodpantry.controller;

import edu.bowiestateuni.groupproj.foodpantry.services.RegistrationService;
import edu.bowiestateuni.groupproj.foodpantry.services.UserManagementService;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.registration.RegistrationRequestDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.CustomerDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.EmployeeDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.EmployeeUpdateRequestDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.UserDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.security.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class UserManagementController {
    private UserManagementService userManagementService;
    private final RegistrationService registrationService;

    @GetMapping(value = "")
    @Secured(value = {"FRONT_DESK", "STAFF", "CEO"})
    public ResponseEntity<Page<UserDTO>> fetchAllUsers(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, final Pageable pageable) {
        final Page<UserDTO> responseDTO = userManagementService.getAllUsers(authenticatedUser, pageable);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping(value = "/employee/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured(value = {"STAFF", "CEO"})
    public ResponseEntity<EmployeeDTO> registerEmployee(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @Valid @RequestBody final RegistrationRequestDTO registrationRequest) {
        final EmployeeDTO responseDTO = registrationService.registerEmployee(authenticatedUser, registrationRequest);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(value = "/employees")
    @Secured(value = {"STAFF", "CEO"})
    public ResponseEntity<Page<EmployeeDTO>> fetchAllEmployees(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, final Pageable pageable) {
        final Page<EmployeeDTO> responseDTO = userManagementService.getAllEmployees(authenticatedUser, pageable);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(value = "/customers")
    @Secured(value = {"FRONT_DESK", "STAFF", "CEO"})
    public ResponseEntity<Page<CustomerDTO>> fetchAllCustomers(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, final Pageable pageable) {
        final Page<CustomerDTO> responseDTO = userManagementService.getAllCustomers(authenticatedUser, pageable);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping(value = "/employee")
    @Secured(value = {"STAFF", "CEO"})
    public ResponseEntity<EmployeeDTO> changeEmployeeRole(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, final @Valid EmployeeUpdateRequestDTO updateRequest) {
        final EmployeeDTO responseDTO = userManagementService.changeEmployeeRole(authenticatedUser, updateRequest);
        return ResponseEntity.ok(responseDTO);
    }
}
