package com.etnetera.hr.dto;

import lombok.Data;

import java.util.Date;

@Data
public class JavaScriptFrameworkUpdateRequest {

    private Long id;
    private String name;
    private Date deprecationDate;
    private HypeLevel hypeLevel;

}
