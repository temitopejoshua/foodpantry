package edu.bowiestateuni.groupproj.foodpantry.entities;

import edu.bowiestateuni.groupproj.foodpantry.entities.constant.DonationStatusTypeConstant;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.FoodTypeConstant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "donations")
public class DonationEntity extends AbstractBaseEntity{
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UserEntity donatedBy;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DonationStatusTypeConstant status = DonationStatusTypeConstant.PENDING;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FoodTypeConstant foodType;

    private int quantityDonated; //Quantity donated in pounds

}
