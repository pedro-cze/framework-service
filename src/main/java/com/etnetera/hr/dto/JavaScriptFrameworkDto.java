package com.etnetera.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 *
 */
@Data
@AllArgsConstructor
public class JavaScriptFrameworkDto {

    private Long id;
    private String name;
    private Date deprecationDate;
    private Set<String> version;
    private HypeLevel hypeLevel;
}
