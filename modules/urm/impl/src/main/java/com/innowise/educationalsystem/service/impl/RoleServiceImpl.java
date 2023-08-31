package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.crud.service.AbstractCrudService;
import com.innowise.educationalsystem.dto.RoleRequestDto;
import com.innowise.educationalsystem.entity.Permission;
import com.innowise.educationalsystem.entity.Role;
import com.innowise.educationalsystem.exception.SystemRoleDeletionException;
import com.innowise.educationalsystem.repository.PermissionRepository;
import com.innowise.educationalsystem.repository.RoleRepository;
import com.innowise.educationalsystem.service.RoleService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;

@Service
public class RoleServiceImpl extends AbstractCrudService<Role, String, RoleRepository> implements RoleService {
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository crudRepository, PermissionRepository permissionRepository) {
        super(crudRepository);
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Role create(RoleRequestDto requestDto) {
        List<Permission> permissions = permissionRepository.findAllById(requestDto.getPermissionIds());
        Role role = Role.builder()
                .id(requestDto.getId())
                .name(requestDto.getName())
                .system(false)
                .permissions(new HashSet<>(permissions))
                .build();

        return crudRepository.save(role);
    }

    @Override
    public Role update(RoleRequestDto requestDto) {
        Role role = crudRepository.findById(requestDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Role with id %s doesn't exist", requestDto.getId())));

        List<Permission> permissions = permissionRepository.findAllById(requestDto.getPermissionIds());

        role.setName(requestDto.getName());
        role.setPermissions(new HashSet<>(permissions));

        return crudRepository.save(role);
    }

    @Override
    public void deleteById(String id) {
        Role role = crudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Role with id %s doesn't exist", id)));

        if (role.isSystem()) {
            throw new SystemRoleDeletionException("Unable to delete system roles");
        }

        crudRepository.delete(role);
    }
}
