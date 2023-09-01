package com.innowise.educationalsystem.repository;

import com.innowise.educationalsystem.crud.repository.CrudRepository;
import com.innowise.educationalsystem.entity.LessonSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonSectionRepository extends JpaRepository<LessonSection, String>, CrudRepository<LessonSection, String> {
}
