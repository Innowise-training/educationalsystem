package com.innowise.educationalsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "invites")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "invite_roles",
               joinColumns = @JoinColumn(name = "fk_invite"),
               inverseJoinColumns = @JoinColumn(name = "fk_role"))
    private Set<Role> roles = new HashSet<>();

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
