package edu.bowiestateuni.groupproj.foodpantry.entities;

import edu.bowiestateuni.groupproj.foodpantry.entities.constant.AuditTrailAction;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "audit_trail")
public class AuditTrailEntity extends AbstractBaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    private String actorEmail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuditTrailAction auditTrailAction;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String oldState;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String currentState;
}
