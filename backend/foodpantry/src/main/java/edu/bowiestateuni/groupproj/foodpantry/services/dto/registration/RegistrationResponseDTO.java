package edu.bowiestateuni.groupproj.foodpantry.services.dto.registration;

import edu.bowiestateuni.groupproj.foodpantry.entities.CustomerEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.EmployeeEntity;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.CustomerDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO {
    private EmployeeDTO employee;
    private CustomerDTO customer;
}
