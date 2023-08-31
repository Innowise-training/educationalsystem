package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.dto.RoleResponseDto;
import com.innowise.educationalsystem.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PermissionMapper.class)
public interface RoleMapper {
    RoleResponseDto entityToDto(Role role);

    List<RoleResponseDto> toDtoList(List<Role> roles);
}
