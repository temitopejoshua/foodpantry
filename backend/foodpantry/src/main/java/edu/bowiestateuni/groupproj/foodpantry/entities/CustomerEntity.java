package edu.bowiestateuni.groupproj.foodpantry.entities;

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
@Table(name = "customers")
public class CustomerEntity extends AbstractBaseEntity{
    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserEntity user;

    @Column(nullable = false)
    private String phoneNumber;

}
