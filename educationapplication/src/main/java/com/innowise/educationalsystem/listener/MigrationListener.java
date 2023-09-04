package com.innowise.educationalsystem.listener;

import com.innowise.educationalsystem.domain.DatabaseUrl;
import com.innowise.educationalsystem.service.DatabaseUrlService;
import com.innowise.educationalsystem.service.MigrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationListener {
    private final DatabaseUrlService databaseUrlService;

    private final MigrationService migrationService;

    @EventListener
    public void applyMigrations(ApplicationReadyEvent applicationReadyEvent) {
        List<DatabaseUrl> databaseList = databaseUrlService.findAll();
        databaseList.forEach(databaseDetails ->
                migrationService.applyMigrationByUrl(databaseDetails.getUrl()));
    }
}
