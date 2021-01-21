package com.etnetera.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class JavaScriptFrameworkDto {

    private Long id;
    private String name;
    private Date deprecationDate;
}
