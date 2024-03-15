package com.ezicrm.eziCRM.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_category", schema = "test_db", catalog = "")
public class GroupCategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "grp_id")
    private long grpId;
    @Basic
    @NotBlank
    @Column(name = "group_name", unique = true)
    private String groupName;
    @UpdateTimestamp
    @Column(name = "updated")
    private Timestamp updated;
    @CreationTimestamp
    @Column(name = "created")
    private Timestamp created;

    @ManyToMany(mappedBy = "groups")
    private Set<CategoryEntity> categories = new HashSet<>();

    public long getGrpId() {
        return grpId;
    }

    public void setGrpId(long grpId) {
        this.grpId = grpId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

        GroupCategoryEntity that = (GroupCategoryEntity) o;

        if (grpId != that.grpId) return false;
        if (groupName != null ? !groupName.equals(that.groupName) : that.groupName != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;

        return true; // loi
    }


    @Override
    public int hashCode() {
        int result = (int) (grpId ^ (grpId >>> 32));
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GroupCategoryEntity{" +
                "grpId=" + grpId +
                ", groupName='" + groupName + '\'' +
                ", updated=" + updated +
                ", created=" + created +
                '}';
    }
}
