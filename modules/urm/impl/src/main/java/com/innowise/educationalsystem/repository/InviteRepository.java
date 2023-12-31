package com.innowise.educationalsystem.repository;

import com.innowise.educationalsystem.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, String> {
    List<Invite> findAllByEmail(String email);
}
