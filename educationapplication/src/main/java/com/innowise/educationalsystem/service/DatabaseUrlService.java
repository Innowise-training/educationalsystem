package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.domain.DatabaseUrl;

import java.util.List;

public interface DatabaseUrlService {
    DatabaseUrl findById(String id);

    List<DatabaseUrl> findAll();
}
