package edu.bowiestateuni.groupproj.foodpantry.services.dto.login;

import edu.bowiestateuni.groupproj.foodpantry.entities.constant.EmployeeRoleTypeConstant;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.UserTypeConstant;

public record LoginResponseDTO(String token, UserTypeConstant userType, String name, EmployeeRoleTypeConstant role) {
}
