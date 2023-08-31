package com.innowise.educationalsystem.course.service.impl;

import com.innowise.educationalsystem.course.annotation.CourseEnrollmentRequired;
import com.innowise.educationalsystem.course.dto.MailRequest;
import com.innowise.educationalsystem.course.dto.UserHaveAuthoritiesRequestDto;
import com.innowise.educationalsystem.course.dto.UserHavePermissionsDto;
import com.innowise.educationalsystem.course.dto.enums.MailType;
import com.innowise.educationalsystem.course.entity.UserCourse;
import com.innowise.educationalsystem.course.entity.UserCourseId;
import com.innowise.educationalsystem.course.kafka.KafkaEmailProducer;
import com.innowise.educationalsystem.course.repository.UserCourseRepository;
import com.innowise.educationalsystem.course.service.CourseEnrolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseEnrolServiceImpl implements CourseEnrolService {
    private final UserCourseRepository userCourseRepository;

    private final KafkaEmailProducer kafkaEmailProducer;

    private final RestTemplate restTemplate;

    private static final String ENROL_STUDENT_PERMISSION = "enrol_student_permission";
    private static final String ENROL_OWNER_PERMISSION = "enrol_owner_permission";
    private static final String EXPEL_STUDENT_PERMISSION = "expel_student_permission";
    private static final String EXPEL_OWNER_PERMISSION = "expel_owner_permission";

    @Override
    @Transactional
    @CourseEnrollmentRequired
    public void enrolUserListInCourse(String courseId, List<String> userIdList) {
        List<String> enrolRequestedAuthorities = new ArrayList<>();
        enrolRequestedAuthorities.add(ENROL_STUDENT_PERMISSION);

        List<UserHavePermissionsDto> userWithPermissionsDetails = getUsersWithRequestedPermissions(userIdList, enrolRequestedAuthorities);

        boolean allNotHaveRequestedPermission = userWithPermissionsDetails.stream()
                .noneMatch(UserHavePermissionsDto::isHasRequestedPermissions);

        if (!allNotHaveRequestedPermission &&
                principalNotHaveRequestedPermission(ENROL_OWNER_PERMISSION)) {
            throw new AccessDeniedException("Access denied"); // TODO: exception handling with message
        }

        userWithPermissionsDetails.forEach(userDto -> {
            userCourseRepository.save(
                    new UserCourse(UserCourseId.builder()
                            .userId(userDto.getUserId())
                            .courseId(courseId)
                            .build()));
            Map<String, Object> payload = new HashMap<>();
            payload.put("courseId", courseId); // TODO: replace with course name ???
            payload.put("message", "You was successfully enrolled in course"); // TODO: replace with course name ???
            notifyUserAboutCourse(userDto.getEmail(), payload); // TODO: How to send mail to users.
        });
    }

    @Override
    @Transactional
    @CourseEnrollmentRequired
    public void expelUserListFromCourse(String courseId, List<String> userIdList) {
        List<String> expelRequestedAuthorities = new ArrayList<>();
        expelRequestedAuthorities.add(EXPEL_STUDENT_PERMISSION);

        List<UserHavePermissionsDto> userWithPermissionsDetails = getUsersWithRequestedPermissions(userIdList, expelRequestedAuthorities);

        boolean allNotHaveRequestedPermission = userWithPermissionsDetails.stream()
                .noneMatch(UserHavePermissionsDto::isHasRequestedPermissions);

        if (!allNotHaveRequestedPermission &&
                principalNotHaveRequestedPermission(EXPEL_OWNER_PERMISSION)) {
            throw new AccessDeniedException("Access denied");
        }

        userWithPermissionsDetails.forEach(userDto -> {
            userCourseRepository.deleteById(UserCourseId.builder()
                    .courseId(courseId)
                    .userId(userDto.getUserId())
                    .build());
            Map<String, Object> payload = new HashMap<>();
            payload.put("courseId", courseId); // TODO: replace with course name ???
            payload.put("message", "You was expelled from course"); // TODO: replace with course name ???
            notifyUserAboutCourse(userDto.getEmail(), payload);
        });
    }

    private boolean principalNotHaveRequestedPermission(String requestedPermission) {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> principalAuthorities = authenticatedUser.getAuthorities();
        return !principalAuthorities.contains(
                new SimpleGrantedAuthority(requestedPermission));
    }

    private List<UserHavePermissionsDto> getUsersWithRequestedPermissions(List<String> userIdList, List<String> requestedPermissions) {
        ResponseEntity<List<UserHavePermissionsDto>> response = restTemplate.exchange(
                URI.create("http://localhost:8080/api/v1/users/have-permissions"),
                HttpMethod.POST,
                new HttpEntity<>(UserHaveAuthoritiesRequestDto.builder()
                        .userIdList(userIdList)
                        .authorityList(requestedPermissions)
                        .build()),
                new ParameterizedTypeReference<List<UserHavePermissionsDto>>() {
                }); // TODO: Replace with FeignClient
        return response.getBody();
    }

    private void notifyUserAboutCourse(String userEmail, Map<String, Object> payload) {
        kafkaEmailProducer.sendEmailRequestToKafka(MailRequest.builder()
                .mailType(MailType.INVITE) // TODO: Replace with another type
                .destinationEmail(userEmail)
                .payload(payload)
                .build());
    }

}
