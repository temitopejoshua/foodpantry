package edu.bowiestateuni.groupproj.foodpantry.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CrudDAO<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    T getById(ID id);
    Page<T> findAll(Pageable pageable);
}
