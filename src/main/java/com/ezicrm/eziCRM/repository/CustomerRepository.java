package com.ezicrm.eziCRM.repository;

import com.ezicrm.eziCRM.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findFirstByCic(String customerCIC);

    @Query("SELECT c FROM CustomerEntity c " +
            "WHERE (:name IS NULL OR c.name LIKE :name) " +
            "AND (:min_age IS NULL OR YEAR(CURRENT_DATE) - YEAR(c.birth) >= :min_age) " +
            "AND (:max_age IS NULL OR YEAR(CURRENT_DATE) - YEAR(c.birth) <= :max_age) " +
            "AND (:address IS NULL OR c.address LIKE :address) " +
            "AND (:phone IS NULL OR c.phone LIKE :phone) " +
            "AND (:email IS NULL OR c.email LIKE :email) " +
            "AND (:facebook IS NULL OR c.facebook LIKE :facebook)")
    List<CustomerEntity> findByProperty(@Param("name") String name,
                                    @Param("min_age") Integer min_age,
                                    @Param("max_age") Integer max_age,
                                    @Param("address") String address,
                                    @Param("phone") String phone,
                                    @Param("email") String email,
                                    @Param("facebook") String facebook);

}
