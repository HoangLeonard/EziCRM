package com.ezicrm.eziCRM.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category", schema = "test_db")
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cat_id", nullable = false)
    private long catId;

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    @Basic
    @NotBlank(message = "Invalid category name, must not be null.")
    @Column(name = "category_name", length = 100, unique = true)
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        if(categoryName != null){
            if(categoryName.isBlank()){
                this.categoryName = null;
            }
            else {
                this.categoryName = categoryName.trim();
            }
        }
        else {
            this.categoryName = null;
        }
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
    @Column(name = "created", nullable = true)
    @CreationTimestamp
    private Timestamp created;

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @ManyToMany(mappedBy = "categories")
    private Set<CustomerEntity> assignedCustomers = new HashSet<>();

    public void addCustomer(CustomerEntity c){
        assignedCustomers.add(c);
    }

    public void deleteCustomer(CustomerEntity c){
        assignedCustomers.remove(c);
    }

    @JsonIgnore
    public List<CustomerEntity> getCustomersToCategory(){
        return assignedCustomers.stream().toList();
    }

    public void setAssCustomers(Set<CustomerEntity> assCustomers) {
        this.assignedCustomers = assCustomers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryEntity that = (CategoryEntity) o;

        if (catId != that.catId) return false;
        if (!Objects.equals(categoryName, that.categoryName)) return false;
        if (!Objects.equals(updated, that.updated)) return false;
        if (!Objects.equals(created, that.created)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (catId ^ (catId >>> 32));
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "CategoryEntity{" +
                "catId=" + catId +
                ", categoryName='" + categoryName + '\'' +
                ", updated=" + updated +
                ", created=" + created +
                '}';
    }
}
