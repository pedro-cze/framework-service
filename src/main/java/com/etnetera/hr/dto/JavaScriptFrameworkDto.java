package com.etnetera.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class JavaScriptFrameworkDto {

    private Long id;
    private String name;
    private LocalDate deprecationDate;
    private Set<String> version;
    private HypeLevel hypeLevel;
}
