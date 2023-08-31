package com.innowise.educationalsystem.course.aspect;


import com.innowise.educationalsystem.course.entity.UserCourseId;
import com.innowise.educationalsystem.course.service.CourseService;
import com.innowise.educationalsystem.course.annotation.CourseEnrollmentRequired;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CourseEnrollmentAspect {
    private final CourseService courseService;

    private static final String AUTH_DETAILS_USER_ID = "userId";

    @After(value = "@annotation(courseEnrollmentRequired)")
    public void checkCourseEnrollment(JoinPoint joinPoint, CourseEnrollmentRequired courseEnrollmentRequired) {
        Object[] methodArgs = joinPoint.getArgs();
        String courseId = (String) methodArgs[0];

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> authenticatedUserDetails = (Map<String, Object>) authentication.getDetails();
        String authenticatedUserId = (String) authenticatedUserDetails.get(AUTH_DETAILS_USER_ID);

        if (!courseService.findById(courseId).isPresent()) {
            throw new EntityNotFoundException("Course not found: " + courseId);
        }

        UserCourseId userCourseId = UserCourseId.builder()
                .userId(authenticatedUserId)
                .courseId(courseId)
                .build();

        if (!courseService.isUserEnrolled(userCourseId)) {
            throw new RuntimeException(courseEnrollmentRequired.message());
        }
    }
}
