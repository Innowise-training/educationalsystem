package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.exception.NoSuchRoleException;
import com.innowise.educationalsystem.entity.Role;
import com.innowise.educationalsystem.entity.User;
import com.innowise.educationalsystem.repository.RoleRepository;
import com.innowise.educationalsystem.repository.UserRepository;
import com.innowise.educationalsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s doesn't exist", id)));
    }

    @Override
    public User assignRoles(String userId, Set<String> roleIds) {
        List<Role> roles = roleRepository.findAllById(roleIds);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s doesn't exist", userId)));

        user.getRoles().addAll(roles);

        return userRepository.save(user);
    }

    @Override
    public User removeRoles(String userId, Set<String> roleIds) {
        List<Role> roles = roleRepository.findAllById(roleIds);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s doesn't exist", userId)));

        roles.stream()
                .filter(role -> !user.getRoles().contains(role))
                .findFirst()
                .ifPresent(role -> {
                    throw new NoSuchRoleException(
                            String.format("User %s doesn't have role %s", userId, role.getName()));
                });

        roles.forEach(user.getRoles()::remove);

        return userRepository.save(user);
    }
}
