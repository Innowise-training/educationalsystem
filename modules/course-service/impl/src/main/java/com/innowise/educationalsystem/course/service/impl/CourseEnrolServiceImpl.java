package com.innowise.educationalsystem.course.service.impl;

import com.innowise.educationalsystem.course.annotation.CourseEnrollmentRequired;
import com.innowise.educationalsystem.course.config.KafkaConfigProperties;
import com.innowise.educationalsystem.course.dto.MailRequest;
import com.innowise.educationalsystem.course.dto.UserHavePermissionsRequestDto;
import com.innowise.educationalsystem.course.dto.UserHavePermissionsResponseDto;
import com.innowise.educationalsystem.course.dto.enums.MailType;
import com.innowise.educationalsystem.course.entity.UserCourse;
import com.innowise.educationalsystem.course.entity.UserCourseId;
import com.innowise.educationalsystem.course.repository.UserCourseRepository;
import com.innowise.educationalsystem.course.service.CourseEnrolService;
import com.innowise.educationalsystem.course.service.NotificationService;
import com.innowise.educationalsystem.course.service.notification.address.NotificationAddress;
import com.innowise.educationalsystem.course.service.notification.address.impl.KafkaSingleTopicNotificationAddress;
import com.innowise.educationalsystem.course.service.notification.impl.SimpleNotification;
import com.innowise.educationalsystem.course.service.notification.payload.NotificationPayload;
import com.innowise.educationalsystem.course.service.notification.payload.impl.MailRequestPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseEnrolServiceImpl implements CourseEnrolService {
    private final UserCourseRepository userCourseRepository;

    @Autowired
    @Qualifier("retryNotificationService")
    private NotificationService notificationService;

    private final KafkaConfigProperties kafkaConfigProperties;

    private static final String KAFKA_MAIL_REQUEST_TOPIC_PROPERTY = "mail-request";

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

        List<UserHavePermissionsResponseDto> userWithPermissionsDetails = getUsersWithRequestedPermissions(userIdList, enrolRequestedAuthorities);

        boolean allNotHaveRequestedPermission = userWithPermissionsDetails.stream()
                .noneMatch(UserHavePermissionsResponseDto::isHavingRequestedPermissions);

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
//            notifyUserAboutCourse(userDto.getEmail(), payload); // TODO: How to send mail to users.
        });
    }

    @Override
    @Transactional
    @CourseEnrollmentRequired
    public void expelUserListFromCourse(String courseId, List<String> userIdList) {
        List<String> expelRequestedAuthorities = new ArrayList<>();
        expelRequestedAuthorities.add(EXPEL_STUDENT_PERMISSION);

        List<UserHavePermissionsResponseDto> userWithPermissionsDetails = getUsersWithRequestedPermissions(userIdList, expelRequestedAuthorities);

        boolean allNotHaveRequestedPermission = userWithPermissionsDetails.stream()
                .noneMatch(UserHavePermissionsResponseDto::isHavingRequestedPermissions);

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
//            notifyUserAboutCourse(userDto.getEmail(), payload);
        });
    }

    private boolean principalNotHaveRequestedPermission(String requestedPermission) {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> principalAuthorities = authenticatedUser.getAuthorities();
        return !principalAuthorities.contains(
                new SimpleGrantedAuthority(requestedPermission));
    }

    private List<UserHavePermissionsResponseDto> getUsersWithRequestedPermissions(List<String> userIdList, List<String> requestedPermissions) {
        ResponseEntity<List<UserHavePermissionsResponseDto>> response = restTemplate.exchange(
                URI.create("http://localhost:8080/api/v1/users/have-permissions"),
                HttpMethod.POST,
                new HttpEntity<>(UserHavePermissionsRequestDto.builder()
                        .userIdList(userIdList)
                        .authorityList(requestedPermissions)
                        .build()),
                new ParameterizedTypeReference<List<UserHavePermissionsResponseDto>>() {
                }); // TODO: Replace with FeignClient
        return response.getBody();
    }

    @Scheduled(fixedRate = 90000)
    public void notifyUserAboutCourse() {
        MailRequest mailRequest = MailRequest.builder()
                .mailType(MailType.WELCOMING_LETTER) // TODO: Replace with another type
                .destinationEmail("email")
                .payload(Collections.singletonMap("key", "value"))
                .build();
        NotificationPayload mailPayload = new MailRequestPayload(mailRequest); // TODO: replace with factory ???
        NotificationAddress mailNotificationAddress = new KafkaSingleTopicNotificationAddress(kafkaConfigProperties.getTopics().get(KAFKA_MAIL_REQUEST_TOPIC_PROPERTY));
        notificationService.sendNotification(new SimpleNotification(mailRequest.getDestinationEmail(), mailPayload, mailNotificationAddress));
    }
}
