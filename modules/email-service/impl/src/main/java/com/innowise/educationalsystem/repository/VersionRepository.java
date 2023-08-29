package com.innowise.educationalsystem.repository;

import com.innowise.educationalsystem.crud.repository.CrudRepository;
import com.innowise.educationalsystem.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, String>, CrudRepository<Version, String> {
}
