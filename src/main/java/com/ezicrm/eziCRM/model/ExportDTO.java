package com.ezicrm.eziCRM.model;

import java.util.Map;

interface Exportable {
    Map<Integer, ExportDTO> getExportData();
}

public class ExportDTO {
    String name;
    Object value;

    public ExportDTO() {
    }

    public ExportDTO(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
