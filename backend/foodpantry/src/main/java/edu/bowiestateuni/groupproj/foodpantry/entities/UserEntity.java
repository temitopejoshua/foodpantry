package edu.bowiestateuni.groupproj.foodpantry.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.UserTypeConstant;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "users")
public class UserEntity extends AbstractBaseEntity{
    @Column(nullable = false, unique = true)
    private String emailAddress;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserTypeConstant userType;

    @Column(nullable = false)
    private String name;

    private ZonedDateTime lastLoggedIn;

}
