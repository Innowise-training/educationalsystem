package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.crud.service.CrudService;
import com.innowise.educationalsystem.dto.RoleRequestDto;
import com.innowise.educationalsystem.entity.Role;

public interface RoleService extends CrudService<Role, String> {
    Role create(RoleRequestDto requestDto);

    Role update(RoleRequestDto requestDto);
}
