package com.innowise.educationalsystem.entity;

import com.innowise.educationalsystem.entity.enums.CreationStatus;
import com.innowise.educationalsystem.entity.enums.MailType;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, columnDefinition = "char(36)")
    private String id;

    @Column(name = "destination_email", nullable = false)
    private String destinationEmail;

    @OneToMany(mappedBy = "mail", fetch = FetchType.LAZY)
    @Exclude
    private List<Version> versions;

    @Enumerated(EnumType.STRING)
    @Column(name = "mail_type", nullable = false)
    private MailType mailType;

    @Enumerated(EnumType.STRING)
    @Column(name = "creation_status", nullable = false)
    private CreationStatus creationStatus;

    @Column(name = "creation_time")
    @CreationTimestamp
    private LocalDateTime creationTime;
}
