package com.etnetera.hr.dto;

import lombok.Data;

import java.util.Date;

@Data
public class JavaScriptFrameworkRequestDto {

    private String name;
    private Date deprecationDate;
    private HypeLevel hypeLevel;

}
