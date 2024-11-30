package edu.bowiestateuni.groupproj.foodpantry.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Data
@ToString
@MappedSuperclass
@EnableJpaAuditing
public abstract class AbstractBaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime dateUpdated;

}
