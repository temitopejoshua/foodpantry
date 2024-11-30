package edu.bowiestateuni.groupproj.foodpantry.dao.repository;

import edu.bowiestateuni.groupproj.foodpantry.entities.AuditTrailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTrailRepository extends JpaRepository<AuditTrailEntity, Long> {
}
