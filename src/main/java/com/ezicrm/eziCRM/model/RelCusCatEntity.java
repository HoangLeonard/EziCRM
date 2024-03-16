package com.ezicrm.eziCRM.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@jakarta.persistence.Table(name = "rel_cus_cat", schema = "test_db", catalog = "")
@jakarta.persistence.IdClass(com.ezicrm.eziCRM.model.RelCusCatEntityPK.class)
public class RelCusCatEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "cus_id", nullable = false)
    private long cusId;

    public long getCusId() {
        return cusId;
    }

    public void setCusId(long cusId) {
        this.cusId = cusId;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "cat_id", nullable = false)
    private long catId;

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelCusCatEntity that = (RelCusCatEntity) o;

        if (cusId != that.cusId) return false;
        if (catId != that.catId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (cusId ^ (cusId >>> 32));
        result = 31 * result + (int) (catId ^ (catId >>> 32));
        return result;
    }
}
