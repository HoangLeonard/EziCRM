package com.ezicrm.eziCRM.service;

import java.util.List;

public interface CRUDService<T> {
    T getByID(Long id);
    List<T> getAll();
    T save(T entity);
    boolean delete(Long id);
}
