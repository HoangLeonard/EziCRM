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
        Arrays.sort(ageRange);
        return ageRange;
    }

    public void setAgeRange(int[] ageRange) {
        this.ageRange = ageRange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
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

