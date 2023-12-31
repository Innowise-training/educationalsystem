package com.innowise.educationalsystem.repository;

import com.innowise.educationalsystem.entity.Role;
import com.innowise.educationalsystem.crud.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String>, CrudRepository<Role, String> {
}
