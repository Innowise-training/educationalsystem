package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.dto.RoleResponseDto;
import com.innowise.educationalsystem.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = PermissionMapper.class)
public interface RoleMapper {
    RoleResponseDto mapToDto(Role role);

    List<RoleResponseDto> mapToDtoList(List<Role> roleList);
}
