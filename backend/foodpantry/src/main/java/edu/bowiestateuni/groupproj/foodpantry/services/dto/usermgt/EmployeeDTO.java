package edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt;

import edu.bowiestateuni.groupproj.foodpantry.entities.constant.EmployeeRoleTypeConstant;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EmployeeDTO {
    private long id;
    private UserDTO user;
    private EmployeeRoleTypeConstant role;
    private LocalDate dateOfBirth;
    private String accountNumber;
    private String routingNumber;
//    public EmployeeDTO(EmployeeEntity entity){
//        this.id = entity.getId();
//        this.user = new UserDTO(entity.getUser());
//        this.role= entity.getEmployeeRole();
//        this.accountNumber= "**********";
//        this.routingNumber= entity.getRoutingNumber();
//        this.dateOfBirth=entity.getDateOfBirth();
//    }
}
