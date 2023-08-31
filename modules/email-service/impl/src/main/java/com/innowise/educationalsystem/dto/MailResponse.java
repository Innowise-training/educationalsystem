package com.innowise.educationalsystem.dto;

import com.innowise.educationalsystem.entity.enums.CreationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailResponse {
    private String mailId;

    private CreationStatus creationStatus;
}
