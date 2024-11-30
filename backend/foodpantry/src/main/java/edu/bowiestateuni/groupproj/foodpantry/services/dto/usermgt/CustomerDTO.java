package edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt;

import edu.bowiestateuni.groupproj.foodpantry.entities.CustomerEntity;
import lombok.Data;

@Data
public class CustomerDTO {
    private long id;
    private String phoneNumber;
    private UserDTO user;
    public CustomerDTO(CustomerEntity entity){
        this.id = entity.getId();
        this.user = new UserDTO(entity.getUser());
        this.phoneNumber = "**********";
    }
}
