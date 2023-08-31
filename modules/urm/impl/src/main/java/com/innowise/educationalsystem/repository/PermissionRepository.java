package com.innowise.educationalsystem.repository;

import com.innowise.educationalsystem.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long>   {
}
