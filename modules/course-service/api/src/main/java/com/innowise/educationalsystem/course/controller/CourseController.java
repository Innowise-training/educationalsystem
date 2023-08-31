package com.innowise.educationalsystem.course.controller;

import com.innowise.educationalsystem.course.dto.CourseDto;
import com.innowise.educationalsystem.course.dto.CreateCourseDto;
import com.innowise.educationalsystem.course.dto.UpdateCourseDto;
import com.innowise.educationalsystem.course.entity.Course;
import com.innowise.educationalsystem.course.mapper.CourseMapper;
import com.innowise.educationalsystem.course.service.impl.CourseServiceImpl;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseServiceImpl courseService;

    private final CourseMapper courseMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('COURSE_GET')")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<Course> courses = courseService.findAll();
        return ResponseEntity.ok().body(courseMapper.toDtoList(courses));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('COURSE_GET')")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable("id") String id) {
        Course course = courseService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        return ResponseEntity.ok().body(courseMapper.courseToCourseDto(course));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('COURSE_MANAGE')")
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CreateCourseDto createCourseDto) {
        Course course = courseMapper.createCourseDtoToCourse(createCourseDto);
        Course createdCourse = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseMapper.courseToCourseDto(createdCourse));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('COURSE_EDIT')")
    public ResponseEntity<CourseDto> updateCourse(@Valid @RequestBody UpdateCourseDto updateCourseDto) {
        Course course = courseMapper.updateCourseDtoToCourse(updateCourseDto);
        Course updatedCourse = courseService.save(course);
        return ResponseEntity.ok().body(courseMapper.courseToCourseDto(updatedCourse));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COURSE_MANAGE')")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
