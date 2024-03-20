package com.ezicrm.eziCRM.model;

import com.ezicrm.eziCRM.model.validator.ValidDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

import static com.ezicrm.eziCRM.model.validator.DateValidator.*;

@Entity
@Table(name = "customer", schema = "test_db")
public class CustomerEntity implements Exportable{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cus_id")
    private long cusId;

    public long getCusId() {
        return cusId;
    }

    public void setCusId(long cusId) {
        this.cusId = cusId;
    }

    @Basic
    @NotEmpty(message = "Invalid name, cannot be empty or null.")
    @Pattern(regexp = "^[\\p{L} .'-]+$",message = "Invalid name, cannot contain uncommon characters or digits.")
    @Column(name = "name", length = 40)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null)
            if (name.isBlank())
                this.name = null;
            else {
                this.name = name.trim();
            }
        else this.name = null;
    }

    @Basic
    @Column(name = "address", length = 100)
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address != null)
            if (address.isBlank())
                this.address = null;
            else this.address = address.trim();
        else this.address = null;
    }

    @Basic
    @ValidDate(message = "Invalid date, must be in the past and age (up to now) is between " + MIN_AGE +" and " + MAX_AGE)
    @Column(name = "birth")
    private Date birth;

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Basic
    @Pattern(regexp = "\\d{7,15}", message = "Invalid phone, must contain 7-15 digits.")
    @Column(name = "phone", length = 20)
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone != null)
            if (phone.isBlank())
                this.phone = null;
            else this.phone = phone.trim();
        else this.phone = null;
    }

    @Basic
    @Email(message = "Invalid email.")
    @Column(name = "email", length = 50)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null)
            if (email.isBlank())
                this.email = null;
            else this.email = email.trim();
        else this.email = null;
    }

    @Basic
    @Pattern(regexp = "^(https?://)?(www\\.)?(facebook\\.com/)(.+)$", message = "Invalid facebook link.")
    @Column(name = "facebook", length = 100)
    private String facebook;

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        if (facebook != null)
            if (facebook.isBlank())
                this.facebook = null;
            else this.facebook = facebook.trim();
        else this.facebook = null;
    }

    @Basic
    @Column(name = "updated")
    @UpdateTimestamp
    private Timestamp updated;

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    @Basic
    @Column(name = "created")
    @CreationTimestamp
    private Timestamp created;

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @ManyToMany(mappedBy = "assignedCustomers")
    private Set<CategoryEntity> categories = new HashSet<>();

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    @Override
    public  Map<Integer, ExportDTO> exportData() {

        Map<Integer, ExportDTO> map =  new HashMap<>();
        map.put(0, new ExportDTO("cusId", cusId));
        map.put(1, new ExportDTO("name", name));
        map.put(2, new ExportDTO("address", address));
        map.put(3, new ExportDTO("birth", birth));
        map.put(4, new ExportDTO("phone", phone));
        map.put(5, new ExportDTO("email", email));
        map.put(6, new ExportDTO("facebook", facebook));
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerEntity that = (CustomerEntity) o;

        if (cusId != that.cusId) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(address, that.address)) return false;
        if (!Objects.equals(birth, that.birth)) return false;
        if (!Objects.equals(phone, that.phone)) return false;
        if (!Objects.equals(email, that.email)) return false;
        if (!Objects.equals(facebook, that.facebook)) return false;
        if (!Objects.equals(updated, that.updated)) return false;
        return Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        int result = (int) (cusId ^ (cusId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (facebook != null ? facebook.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "cusId=" + cusId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", birth=" + birth +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", facebook='" + facebook + '\'' +
                ", updated=" + updated +
                ", created=" + created +
                ", categories=" + categories +
                '}';
    }

}
