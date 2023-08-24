package com.innowise.educationalsystem.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invites")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Invite {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private InviteStatus status;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    private String roles;

    @Column(name = "subscription_id", nullable = false)
    private Long subscriptionId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "validation_expired_at")
    private LocalDateTime validationExpiredAt;

    @Column(name = "registration_expired_at")
    private LocalDateTime registrationExpiredAt;
}
