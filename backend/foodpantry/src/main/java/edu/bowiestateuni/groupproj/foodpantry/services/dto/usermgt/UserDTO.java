package edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt;

import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.UserTypeConstant;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UserDTO {
    private String name;
    private String emailAddress;
    private UserTypeConstant userType;
    private ZonedDateTime lastLoggedInDate;
    public UserDTO (UserEntity userEntity){
        this.name = userEntity.getName();
        this.emailAddress = userEntity.getEmailAddress();
        this.userType = userEntity.getUserType();
        this.lastLoggedInDate = userEntity.getLastLoggedIn();
    }
}
