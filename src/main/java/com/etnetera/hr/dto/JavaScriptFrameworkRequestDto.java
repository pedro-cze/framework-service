package com.etnetera.hr.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class JavaScriptFrameworkRequestDto {

    private String name;
    private Date deprecationDate;
    private HypeLevel hypeLevel;
    private Set<String> version;

}
