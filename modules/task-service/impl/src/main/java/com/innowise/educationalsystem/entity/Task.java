package com.innowise.educationalsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, columnDefinition = "char(36)")
    private String id;

    @Column(name = "type_name", nullable = false)
    private String typeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_subtype")
    @ToString.Exclude
    private TaskSubtype taskSubtype;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_section")
    @ToString.Exclude
    private LessonSection lessonSection;

    @Embedded
    private Audit audit = new Audit();
}
