package com.innowise.educationalsystem.course.repository;

import com.innowise.educationalsystem.course.entity.Course;
import com.innowise.educationalsystem.crud.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String>, CrudRepository<Course, String> {

}
