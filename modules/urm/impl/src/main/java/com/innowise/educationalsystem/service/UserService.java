package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAll();

    User getById(String id);

    User assignRoles(String userId, Set<String> roleIds);

    User removeRoles(String userId, Set<String> roleIds);
}
