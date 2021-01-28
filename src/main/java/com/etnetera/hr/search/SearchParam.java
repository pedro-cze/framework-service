package com.etnetera.hr.search;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchParam {

    private String paramName;
    private String operation;
    private Object value;

}
