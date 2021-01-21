package com.etnetera.hr.mapper;

import com.etnetera.hr.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.entity.JavaScriptFramework;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DomainMapper {

    JavaScriptFramework toEntity(JavaScriptFrameworkDto dto);

    JavaScriptFrameworkDto toDto(JavaScriptFramework entity);

    List<JavaScriptFramework> toEntities(List<JavaScriptFrameworkDto> dtos);

    List<JavaScriptFrameworkDto> toDtos(List<JavaScriptFramework> entities);

}
