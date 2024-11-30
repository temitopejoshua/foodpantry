package edu.bowiestateuni.groupproj.foodpantry.dao.impl;

import edu.bowiestateuni.groupproj.foodpantry.dao.AuditTrailDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.repository.AuditTrailRepository;
import edu.bowiestateuni.groupproj.foodpantry.entities.AuditTrailEntity;
import edu.bowiestateuni.groupproj.foodpantry.services.impl.CrudDAOImpl;
import org.springframework.stereotype.Service;

@Service
public class AuditTrailDAOImpl extends CrudDAOImpl<AuditTrailEntity, Long> implements AuditTrailDAO {
    private final AuditTrailRepository repository;

    public AuditTrailDAOImpl(AuditTrailRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
