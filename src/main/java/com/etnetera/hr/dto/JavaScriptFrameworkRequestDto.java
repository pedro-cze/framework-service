package com.etnetera.hr.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class JavaScriptFrameworkRequestDto {

    private String name;
    private LocalDate deprecationDate;
    private HypeLevel hypeLevel;
    private Set<String> version;

}
