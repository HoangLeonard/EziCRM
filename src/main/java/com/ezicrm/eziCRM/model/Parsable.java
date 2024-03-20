package com.ezicrm.eziCRM.model;

import java.util.List;

public interface Parsable <T>{
    T parse(List<String> text);
}
