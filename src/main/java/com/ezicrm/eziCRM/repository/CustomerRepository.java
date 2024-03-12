package com.ezicrm.eziCRM.repository;

import com.ezicrm.eziCRM.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
