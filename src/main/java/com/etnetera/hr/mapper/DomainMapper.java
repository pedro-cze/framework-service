package com.etnetera.hr.mapper;

import com.etnetera.hr.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.dto.JavaScriptFrameworkRequestDto;
import com.etnetera.hr.dto.JavaScriptFrameworkUpdateRequest;
import com.etnetera.hr.entity.JavaScriptFramework;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DomainMapper {

    @Mappings({
            @Mapping(target = "deprecationDate", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(target = "id", ignore = true)
    })
    JavaScriptFramework toEntity(JavaScriptFrameworkRequestDto dto);

    JavaScriptFramework toEntity(JavaScriptFrameworkUpdateRequest dto);

    JavaScriptFrameworkDto toDto(JavaScriptFramework entity);

    List<JavaScriptFrameworkDto> toDtos(List<JavaScriptFramework> entities);

}
