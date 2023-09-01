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
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, columnDefinition = "char(36)")
    private String id;

    @Column(name = "lesson", nullable = false)
    private String lesson;

    @Column(name = "comment", nullable = false)
    private String comment;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<LessonSection> sections;

    @Embedded
    private Audit audit = new Audit();
}
