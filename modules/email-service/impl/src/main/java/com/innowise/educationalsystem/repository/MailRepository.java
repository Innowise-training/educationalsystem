package com.innowise.educationalsystem.repository;

import com.innowise.educationalsystem.crud.repository.CrudRepository;
import com.innowise.educationalsystem.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail, String>, CrudRepository<Mail, String> {
}
