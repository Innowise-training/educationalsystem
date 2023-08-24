package com.innowise.educationalsystem.course.service.impl;
import com.innowise.educationalsystem.course.entity.Course;
import com.innowise.educationalsystem.course.repository.CourseRepository;
import com.innowise.educationalsystem.course.service.CourseService;
import com.innowise.educationalsystem.crud.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends AbstractCrudService<Course, String, CourseRepository> implements CourseService {
    public CourseServiceImpl(CourseRepository crudRepository) {
        super(crudRepository);
    }
}
