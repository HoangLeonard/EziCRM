package com.ezicrm.eziCRM.service;

import java.util.List;
import java.util.Optional;

public interface CRUDService<T> {
    Optional<T> getByID(Long id);
    List<T> getAll();

    Optional<T> update(T entity);

    Optional<T> insert(T entity);

    boolean delete(Long id);
}
