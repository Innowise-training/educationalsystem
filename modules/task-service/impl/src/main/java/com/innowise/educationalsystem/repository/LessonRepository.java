package com.innowise.educationalsystem.repository;

import com.innowise.educationalsystem.crud.repository.CrudRepository;
import com.innowise.educationalsystem.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, String>, CrudRepository<Lesson, String> {
}
