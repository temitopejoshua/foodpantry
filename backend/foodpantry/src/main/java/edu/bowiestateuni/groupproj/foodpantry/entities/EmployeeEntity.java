package edu.bowiestateuni.groupproj.foodpantry.entities;

import edu.bowiestateuni.groupproj.foodpantry.entities.constant.RoleTypeConstant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "employees")
public class EmployeeEntity extends AbstractBaseEntity{
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    private UserEntity user;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    private LocalDate dateJoined;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleTypeConstant employeeRole;

    private String ssn;
    @Column(nullable = false)
    private String accountNumber;
    @Column(nullable = false)
    private String routingNumber;
}
