package com.innowise.educationalsystem.course.service;

import com.innowise.educationalsystem.course.entity.Course;
import com.innowise.educationalsystem.course.entity.UserCourseId;
import com.innowise.educationalsystem.crud.service.CrudService;

public interface CourseService extends CrudService<Course, String> {

    boolean isUserEnrolled(UserCourseId userCourseId);
}
