package com.innowise.educationalsystem.repository;

import com.innowise.educationalsystem.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
