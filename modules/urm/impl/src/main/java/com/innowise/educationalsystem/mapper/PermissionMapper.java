package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.dto.PermissionResponseDto;
import com.innowise.educationalsystem.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponseDto entityToDto(Permission permission);
}
