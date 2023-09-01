package com.innowise.educationalsystem.repository;

import com.innowise.educationalsystem.crud.repository.CrudRepository;
import com.innowise.educationalsystem.entity.TaskSubtype;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskSubtypeRepository extends JpaRepository<TaskSubtype, String>, CrudRepository<TaskSubtype, String> {
}
