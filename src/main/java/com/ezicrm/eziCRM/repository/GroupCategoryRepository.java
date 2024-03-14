package com.ezicrm.eziCRM.repository;

import com.ezicrm.eziCRM.model.GroupCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupCategoryRepository extends JpaRepository<GroupCategoryEntity, Long> {
    boolean existsByGroupName(String groupName);
}
