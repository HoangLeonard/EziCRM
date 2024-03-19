package com.ezicrm.eziCRM.model;

import java.util.Map;

public interface Exportable {
    Map<Integer, ExportDTO> getExportedData();
}
