package com.innowise.educationalsystem.course.repository;

import com.innowise.educationalsystem.course.entity.UserCourse;
import com.innowise.educationalsystem.course.entity.UserCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseId> {
}
