package com.ezicrm.eziCRM.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "category", schema = "test_db", catalog = "")
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cat_id")
    private long catId;
    @Basic
    @NotBlank
    @Column(name = "label_name", unique = true)
    private String labelName;
    @UpdateTimestamp
    @Column(name = "updated")
    private Timestamp updated;
    @CreationTimestamp
    @Column(name = "created")
    private Timestamp created;

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryEntity that = (CategoryEntity) o;

        if (catId != that.catId) return false;
        if (labelName != null ? !labelName.equals(that.labelName) : that.labelName != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (catId ^ (catId >>> 32));
        result = 31 * result + (labelName != null ? labelName.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "catId=" + catId +
                ", labelName='" + labelName + '\'' +
                ", updated=" + updated +
                ", created=" + created +
                '}';
    }
}
