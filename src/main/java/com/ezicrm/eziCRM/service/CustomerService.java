package com.ezicrm.eziCRM.service;

import com.ezicrm.eziCRM.model.CusSearchReqDTO;
import com.ezicrm.eziCRM.model.CustomerEntity;
import com.ezicrm.eziCRM.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.*;

import java.util.*;

@Service
public class CustomerService implements CRUDService<CustomerEntity> {

    private final CustomerRepository repository;

    @PersistenceContext
    private final EntityManager entityManager;
    private final Validator validator;


    public CustomerService(CustomerRepository repository, EntityManager entityManager, Validator validator) {
        this.repository = repository;
        this.entityManager = entityManager;
        this.validator = validator;
    }

    @Override
    public Optional<CustomerEntity> getByID(Long id) throws IllegalArgumentException {
        return repository.findById(id);
    }

    @Override
    public List<CustomerEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<CustomerEntity> update(CustomerEntity newCustomer) {
        long id = newCustomer.getCusId();

        Optional<CustomerEntity> foundCustomer = repository.findById(id);

        if (foundCustomer.isPresent() && id != 0) {
            CustomerEntity oldCustomer = foundCustomer.get();

            String newPhone = newCustomer.getPhone();
            String newEmail = newCustomer.getEmail();
            String newFacebook = newCustomer.getFacebook();

            if ((newPhone == null || !newPhone.equals(oldCustomer.getPhone()))
                || (newEmail == null || !newEmail.equals(oldCustomer.getEmail()))
                || (newFacebook == null || !newFacebook.equals(oldCustomer.getFacebook()))) {
                checkUpdateCondition(newCustomer);
            }
            return Optional.of(repository.save(newCustomer));
        } else {
            return insert(newCustomer);
        }
    }

    private void checkUpdateCondition(CustomerEntity entity) {
        String phone = entity.getPhone();
        String email = entity.getEmail();
        String facebook = entity.getFacebook();

        // Kiểm tra xem có ít nhất một phương thức liên lạc không null
        if (phone == null && email == null && facebook == null) {
            throw new IllegalArgumentException("Invalid customer, customer must have at least one contact method.");
        }

        // Kiểm tra xem có bản ghi nào đã tồn tại với các thông tin liên lạc của khách hàng mới
        List<CustomerEntity> foundEntities = repository.findByPhoneOrFacebookOrEmail(phone, facebook, email);
        if (foundEntities.size() > 1) {
            StringBuilder errorMessage = new StringBuilder("Invalid customer's contact methods:");
            int p = 0, f = 0, m = 0;
            for (CustomerEntity customer: foundEntities)
                if (customer.getCusId() != entity.getCusId()) {
                    if (customer.getEmail().equals(entity.getEmail())) m++;
                    if (customer.getPhone().equals(entity.getPhone())) p++;
                    if (customer.getFacebook().equals(entity.getFacebook())) f++;
                }

            if (p > 0) errorMessage.append(" - duplicated phone number");
            if (m > 0) errorMessage.append(" - duplicated email");
            if (f > 0) errorMessage.append(" - duplicated facebook url");

            throw new IllegalArgumentException(errorMessage.toString());
        }
    }

    @Override
    public Optional<CustomerEntity> insert(CustomerEntity entity) {
        checkInsertCondition(entity);
        return Optional.of(repository.save(entity));
    }

    private void checkInsertCondition(CustomerEntity entity) {
        String phone = entity.getPhone();
        String email = entity.getEmail();
        String facebook = entity.getFacebook();

        // Kiểm tra xem có ít nhất một phương thức liên lạc không null
        if (phone == null && email == null && facebook == null) {
            throw new IllegalArgumentException("Invalid customer, customer must have at least one contact method.");
        }

        // Kiểm tra xem có bản ghi nào đã tồn tại với các thông tin liên lạc của khách hàng mới
        List<CustomerEntity> foundEntities = repository.findByPhoneOrFacebookOrEmail(phone, facebook, email);
        if (!foundEntities.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Invalid customer's contact methods:");

            int p = 0, f = 0, m = 0;
            for (CustomerEntity customer: foundEntities)
                if (customer.getCusId() != entity.getCusId()) {
                    if (customer.getEmail().equals(entity.getEmail())) m++;
                    if (customer.getPhone().equals(entity.getPhone())) p++;
                    if (customer.getFacebook().equals(entity.getFacebook())) f++;
                }

            if (p > 0) errorMessage.append(" - duplicated phone number");
            if (m > 0) errorMessage.append(" - duplicated email");
            if (f > 0) errorMessage.append(" - duplicated facebook url");

            throw new IllegalArgumentException(errorMessage.toString());
        }
    }

    @Override
    public boolean delete(Long id) throws IllegalArgumentException {
        Optional<CustomerEntity> c = getByID(id);
        if (c.isPresent()) {
            repository.deleteById(id);
            return true;
        } else return false;
    }

    public List<CustomerEntity> getCustomerByProperty(CusSearchReqDTO searchReqDTO) {
        if (searchReqDTO == null) searchReqDTO = new CusSearchReqDTO();
        return repository.findByProperty(searchReqDTO.getName(),
                searchReqDTO.getAgeRange()[0],
                searchReqDTO.getAgeRange()[1],
                searchReqDTO.getAddress(),
                searchReqDTO.getPhone(),
                searchReqDTO.getEmail(),
                searchReqDTO.getFacebook());
    }

    public List<CustomerEntity> getAllByID(List<Long> ids) {
        return repository.findAllById(ids);
    }

    public List<CustomerEntity> findByCategoryIds(List<Long> categoryIds){
        List<CustomerEntity> customers = repository.findByCategoryIds(categoryIds);
        if(customers.isEmpty()){
            return null;
        }
        return customers;
    }

    public List<CustomerEntity> customerParsing(List<List<String>> listCustomer) {
        List<CustomerEntity> customers = new ArrayList<>();

        int count = 1;
        for (List<String> l: listCustomer) {
            CustomerEntity c = new CustomerEntity();
            c.parse(l);
            c.setCusId(count);
            validateCustomer(c);
            customers.add(c);
            count++;
        }

        return customers;
    }

    public void validateCustomer(CustomerEntity customer) {
        // Sử dụng Hibernate Validator để thực hiện validation trên customer
        customer.getErrors().addAll(validator.validateObject(customer).getAllErrors());
    }

    @Transactional
    public void createTemporaryTable() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS tmp_customer").executeUpdate();
        entityManager.createNativeQuery(
                "CREATE TABLE tmp_customer (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(40) DEFAULT NULL, " +
                        "address VARCHAR(100) DEFAULT NULL, " +
                        "birth DATE DEFAULT NULL, " +
                        "phone VARCHAR(20) DEFAULT NULL, " +
                        "email VARCHAR(50) DEFAULT NULL, " +
                        "facebook VARCHAR(100) DEFAULT NULL)"
        ).executeUpdate();
    }

    @Transactional
    public void addToTmpCustomers(List<CustomerEntity> customers) {
        for (CustomerEntity customer : customers) {
            String sql = "INSERT INTO tmp_customer (name, address, birth, phone, email, facebook) VALUES (?, ?, ?, ?, ?, ?)";
            entityManager.createNativeQuery(sql)
                    .setParameter(1, customer.getName())
                    .setParameter(2, customer.getAddress())
                    .setParameter(3, customer.getBirth())
                    .setParameter(4, customer.getPhone())
                    .setParameter(5, customer.getEmail())
                    .setParameter(6, customer.getFacebook())
                    .executeUpdate();
        }
    }

    @Transactional
    public void checkDuplicate(List<CustomerEntity> customers) {
        entityManager.createNativeQuery("DROP TEMPORARY TABLE IF EXISTS contacts").executeUpdate();
        entityManager.createNativeQuery("CREATE TEMPORARY TABLE contacts " +
                "SELECT concat('N', id) as id, phone, email, facebook " +
                "FROM tmp_customer " +
                "UNION ALL " +
                "SELECT concat('O', cus_id) as id, phone, email, facebook " +
                "FROM customer").executeUpdate();

        entityManager.createNativeQuery("DROP TEMPORARY TABLE IF EXISTS dup_phone").executeUpdate();
        entityManager.createNativeQuery("CREATE TEMPORARY TABLE dup_phone " +
                "SELECT CONCAT(GROUP_CONCAT(id ORDER BY id ASC SEPARATOR ','), ',p') AS id, COUNT(*) as count_phone " +
                "FROM contacts " +
                "GROUP BY phone " +
                "HAVING COUNT(*) > 1").executeUpdate();

        entityManager.createNativeQuery("DROP TEMPORARY TABLE IF EXISTS dup_email").executeUpdate();
        entityManager.createNativeQuery("CREATE TEMPORARY TABLE dup_email " +
                "SELECT CONCAT(GROUP_CONCAT(id ORDER BY id ASC SEPARATOR ','), ',e') AS id, COUNT(*) as count_email " +
                "FROM contacts " +
                "GROUP BY email " +
                "HAVING COUNT(*) > 1").executeUpdate();

        entityManager.createNativeQuery("DROP TEMPORARY TABLE IF EXISTS dup_facebook").executeUpdate();
        entityManager.createNativeQuery("CREATE TEMPORARY TABLE dup_facebook " +
                "SELECT CONCAT(GROUP_CONCAT(id ORDER BY id ASC SEPARATOR ','), ',f') AS id, COUNT(*) as count_facebook " +
                "FROM contacts " +
                "GROUP BY facebook " +
                "HAVING COUNT(*) > 1").executeUpdate();

        entityManager.createNativeQuery("DROP TEMPORARY TABLE IF EXISTS ids").executeUpdate();
        entityManager.createNativeQuery("CREATE TEMPORARY TABLE ids " +
                "SELECT * " +
                "FROM ( " +
                "    SELECT id FROM dup_phone " +
                "    UNION " +
                "    SELECT id FROM dup_email " +
                "    UNION " +
                "    SELECT id FROM dup_facebook " +
                ") as ids").executeUpdate();
        // Lấy kết quả cuối cùng
        System.out.println("-------------------------------------------");
        List<Object> result = entityManager.createNativeQuery("SELECT * FROM ids").getResultList();
        for (Object row : result) {
            System.out.println(row);
        }
    }
}
