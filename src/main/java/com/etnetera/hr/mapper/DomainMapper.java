package com.etnetera.hr.mapper;

import com.etnetera.hr.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.dto.JavaScriptFrameworkRequestDto;
import com.etnetera.hr.dto.JavaScriptFrameworkUpdateRequest;
import com.etnetera.hr.entity.JavaScriptFramework;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DomainMapper {

    @Mapping(target = "deprecationDate", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    JavaScriptFramework toEntity(JavaScriptFrameworkRequestDto dto);

    JavaScriptFramework toEntity(JavaScriptFrameworkUpdateRequest dto);

    JavaScriptFrameworkDto toDto(JavaScriptFramework entity);

//    List<JavaScriptFramework> toEntities(List<JavaScriptFrameworkDto> dtos);

    List<JavaScriptFrameworkDto> toDtos(List<JavaScriptFramework> entities);

}
