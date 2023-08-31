package com.innowise.educationalsystem.repository;

import com.innowise.educationalsystem.crud.repository.CrudRepository;
import com.innowise.educationalsystem.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, CrudRepository<User, String> {

    @EntityGraph(value = "fetchUserWithRoleAndPermissions")
    Optional<User> findUserWithRolesAndPermissionsById(String id);

    Optional<User> findUserByUsername(String username);

    Optional<User> findByEmail(String email);

    @EntityGraph(value = "fetchUserWithRoleAndPermissions")
    Optional<User> findUserWithRolesAndPermissionsByUsername(String username);
}
