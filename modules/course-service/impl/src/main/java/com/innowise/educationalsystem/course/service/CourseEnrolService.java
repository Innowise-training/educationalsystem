package com.innowise.educationalsystem.course.service;

import java.util.List;

public interface CourseEnrolService {

    void enrolUserListInCourse(String courseId, List<String> userIdList);

    void expelUserListFromCourse(String courseId, List<String> userIdList);
}
