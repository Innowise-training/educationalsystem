package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.dto.PermissionResponseDto;
import com.innowise.educationalsystem.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapper {
    PermissionResponseDto mapToDto(Permission permission);

    List<PermissionResponseDto> mapToDtoList(List<Permission> permissionList);
}
