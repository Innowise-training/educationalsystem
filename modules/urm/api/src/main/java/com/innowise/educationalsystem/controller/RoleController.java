package com.innowise.educationalsystem.controller;

import com.innowise.educationalsystem.dto.RoleRequestDto;
import com.innowise.educationalsystem.dto.RoleResponseDto;
import com.innowise.educationalsystem.entity.Role;
import com.innowise.educationalsystem.mapper.RoleMapper;
import com.innowise.educationalsystem.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    private final RoleMapper roleMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_GET')")
    public ResponseEntity<List<RoleResponseDto>> getAll() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok().body(roleMapper.toDtoList(roles));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_GET')")
    public ResponseEntity<RoleResponseDto> getById(@PathVariable("id") String id) {
        Role role = roleService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
        return ResponseEntity.ok().body(roleMapper.entityToDto(role));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    public ResponseEntity<RoleResponseDto> create(@Valid @RequestBody RoleRequestDto requestDto) {
        Role role = roleService.create(requestDto);
        return ResponseEntity.status(CREATED).body(roleMapper.entityToDto(role));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    public ResponseEntity<RoleResponseDto> update(@Valid @RequestBody RoleRequestDto requestDto) {
        Role role = roleService.update(requestDto);
        return ResponseEntity.ok().body(roleMapper.entityToDto(role));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
