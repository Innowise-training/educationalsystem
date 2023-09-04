package com.innowise.educationalsystem.controller;

import com.innowise.educationalsystem.service.MigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/migrations")
public class MigrationController {
    private final MigrationService migrationService;

    /**
     * @param databaseUrlId - database url id to apply migrations for url {@link com.innowise.educationalsystem.domain.DatabaseUrl}
     *                      Use that to apply migrations after creating new subscription(new schema - migrations for schema)
     */
    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void applyMigrations(@PathVariable("id") String databaseUrlId) {
        migrationService.applyMigrationById(databaseUrlId);
    }

}
