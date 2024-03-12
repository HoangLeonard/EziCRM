package com.ezicrm.eziCRM.repository;

import com.ezicrm.eziCRM.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
