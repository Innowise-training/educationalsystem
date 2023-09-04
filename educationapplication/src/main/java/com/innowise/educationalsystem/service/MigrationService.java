package com.innowise.educationalsystem.service;

public interface MigrationService {

    void applyMigrationById(String databaseUrlId);

    void applyMigrationByUrl(String databaseUrl);
}
