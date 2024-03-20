package com.ezicrm.eziCRM.repository;

import com.ezicrm.eziCRM.model.CategoryEntity;
import com.ezicrm.eziCRM.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("SELECT c FROM CustomerEntity c " +
            "WHERE (:name IS NULL OR c.name LIKE :name) " +
            "AND (YEAR(CURRENT_DATE) - YEAR(c.birth) >= :min_age) " +
            "AND (YEAR(CURRENT_DATE) - YEAR(c.birth) <= :max_age) " +
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

    List<CustomerEntity> findCustomerEntitiesByCusIdIn(Collection<Long> cusId);

    @Query("SELECT c FROM CustomerEntity c " +
            "WHERE (c.phone IS NOT NULL AND c.phone = :phone) " +
            "OR (c.facebook IS NOT NULL AND c.facebook = :facebook) " +
            "OR (c.email IS NOT NULL AND c.email = :email)")
    List<CustomerEntity> findByPhoneOrFacebookOrEmail(@Param("phone") String phone,
                                                      @Param("facebook") String facebook,
                                                      @Param("email") String email);

    @Query("SELECT c FROM CustomerEntity c JOIN c.categories cat WHERE cat.catId IN :categoryIds")
    List<CustomerEntity> findByCategoryIds(@Param("categoryIds") List<Long> categoryIds);
}
