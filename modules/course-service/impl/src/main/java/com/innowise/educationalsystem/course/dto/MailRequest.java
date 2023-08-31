package com.innowise.educationalsystem.course.dto;

import com.innowise.educationalsystem.course.dto.enums.MailType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailRequest {
    private MailType mailType;

    private String destinationEmail;

    private Map<String, Object> payload;
}
