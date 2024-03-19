package com.ezicrm.eziCRM.model;

import java.util.Arrays;

public class CusSearchReqDTO {
    private String name;
    private int[] ageRange;
    private String address;
    private String phone;
    private String email;
    private String facebook;

    public int[] getAgeRange() {
        if (ageRange != null) {
            Arrays.sort(ageRange);
            return ageRange;
        }
        return new int[]{0, 100};
    }

    public void setAgeRange(int[] ageRange) {
        this.ageRange = ageRange;
    }

    public String getName() {
        if (name != null)
            return "%" + name + "%";
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        if (address != null)
            return "%" + address + "%";
        return null;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        if (phone != null)
            return "%" + phone + "%";
        return null;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        if (email != null)
            return "%" + email + "%";
        return null;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        if (facebook != null)
            return "%" + facebook + "%";
        return null;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    @Override
    public String toString() {
        return "CusSearchReqDTO{" +
                "name='" + name + '\'' +
                ", ageRange=" + Arrays.toString(ageRange) +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", facebook='" + facebook + '\'' +
                '}';
    }
}

