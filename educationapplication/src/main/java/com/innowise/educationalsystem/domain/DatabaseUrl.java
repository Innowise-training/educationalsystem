package com.innowise.educationalsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "database_urls")
public class DatabaseUrl {

    @Id
    @Column(name = "id")
    private String id;

    @Column(nullable = false, unique = true)
    private String url;
}
