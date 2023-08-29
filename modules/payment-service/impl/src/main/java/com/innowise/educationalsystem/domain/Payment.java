package com.innowise.educationalsystem.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "subscriptionId")
    private long subscriptionId;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "currency")
    private String currency;

    @Column(name = "callBackUri")
    private String callBackUri;

    @CreationTimestamp
    @Column(name = "paymentStartedAt")
    private LocalDate paymentStartedAt;

    @UpdateTimestamp
    @Column(name = "paymentUpdatedAt")
    private LocalDate paymentUpdatedAt;
}
