package com.innowise.educationalsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "subscription_id", nullable = false)
    private Long subscriptionId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "fk_user"),
               inverseJoinColumns = @JoinColumn(name = "fk_role"))
    private Set<Role> roles = new HashSet<>();
}
