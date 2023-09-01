package com.innowise.educationalsystem.course.controller;

import com.innowise.educationalsystem.course.service.CourseEnrolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseEnrolController {
    private final CourseEnrolService courseEnrolService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("{id}/members/enrol")
    @PreAuthorize("hasAuthority('course_enrol_permission')")
    public void enrolUserListInCourse(@RequestBody @NotEmpty List<String> userIdList,
                                      @PathVariable("id") String courseId) {
        courseEnrolService.enrolUserListInCourse(courseId, userIdList);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}/members/enrol")
    @PreAuthorize("hasAuthority('course_expel_permission')")
    public void expelUserListFromCourse(@RequestBody @NotEmpty List<String> userIdList,
                                        @PathVariable("id") String courseId) {
        courseEnrolService.expelUserListFromCourse(courseId, userIdList);
    }
}
