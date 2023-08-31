package com.innowise.educationalsystem.repository;

import com.innowise.educationalsystem.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph(value = "fetchUserWithRoleAndPermissions")
    @Query(value = "SELECT user FROM User user WHERE user.id = ?1")
    Optional<User> findByIdWithRolesAndPermissions(String id);

    Optional<User> findUserByUsername(String username);

    Optional<User> findByEmail(String email);

    @EntityGraph(value = "fetchUserWithRoleAndPermissions")
    @Query(value = "SELECT user FROM User user where user.username = ?1")
    Optional<User> findUserByUsernameWithRolesAndPermissions(String username);
}
