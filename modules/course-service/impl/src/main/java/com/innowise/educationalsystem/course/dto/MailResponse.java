package com.innowise.educationalsystem.course.dto;

import com.innowise.educationalsystem.course.dto.enums.CreationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MailResponse {
    private String mailId;

    private CreationStatus creationStatus;
}
