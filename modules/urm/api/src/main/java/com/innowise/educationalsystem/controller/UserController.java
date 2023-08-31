package com.innowise.educationalsystem.controller;

import com.innowise.educationalsystem.dto.UserResponseDto;
import com.innowise.educationalsystem.entity.User;
import com.innowise.educationalsystem.mapper.UserMapper;
import com.innowise.educationalsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('USER_GET')")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok().body(userMapper.toDtoList(users));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_GET')")
    public ResponseEntity<UserResponseDto> getById(@PathVariable("id") String id) {
        User user = userService.getById(id);
        return ResponseEntity.ok().body(userMapper.entityToDto(user));
    }

    @PostMapping("/{id}/assign-roles")
    @PreAuthorize("hasAuthority('USER_ASSIGN_ROLE')")
    public ResponseEntity<UserResponseDto> assignRoles(
            @PathVariable("id") String id,
            @RequestParam(value = "roleId") Set<String> roleIds) {
        User user = userService.assignRoles(id, roleIds);
        return ResponseEntity.ok().body(userMapper.entityToDto(user));
    }

    @DeleteMapping("/{id}/remove-roles")
    @PreAuthorize("hasAuthority('USER_ASSIGN_ROLE')")
    public ResponseEntity<UserResponseDto> removeRoles(
            @PathVariable("id") String id,
            @RequestParam(value = "roleId") Set<String> roleIds) {
        User user = userService.removeRoles(id, roleIds);
        return ResponseEntity.ok().body(userMapper.entityToDto(user));
    }
}
