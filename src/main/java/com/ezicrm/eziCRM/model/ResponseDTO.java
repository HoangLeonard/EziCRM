package com.ezicrm.eziCRM.model;

import java.util.HashMap;
import java.util.Map;

public class ResponseDTO implements Exportable{
    private String status;
    private String message;
    private Object data;

    public ResponseDTO() {}

    public ResponseDTO(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public Map<Integer, ExportDTO> exportData() {
        Map<Integer, ExportDTO> map =  new HashMap<>();
        map.put(0, new ExportDTO("line", this.status));
        map.put(1, new ExportDTO("errors", this.message));
        return map;
    }
}
