package com.ezicrm.eziCRM.model;

import java.util.Map;

public class ExportDTO {
    String name;
    Object value;

    public ExportDTO() {
    }

    public ExportDTO(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
