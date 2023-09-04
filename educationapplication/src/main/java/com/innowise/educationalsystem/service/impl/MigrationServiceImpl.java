package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.domain.DatabaseUrl;
import com.innowise.educationalsystem.service.DatabaseUrlService;
import com.innowise.educationalsystem.service.MigrationService;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
@RequiredArgsConstructor
public class MigrationServiceImpl implements MigrationService {
    private final DatabaseUrlService databaseUrlService;

    private static final String MIGRATION_MASTER_CHANGELOG_PROPERTY = "${app.migrations.changelog-master}";

    @Value(MIGRATION_MASTER_CHANGELOG_PROPERTY)
    private String changelogMaster;
    private static final String MIGRATION_URI_PREFIX = "classpath:";

    @Override
    public void applyMigrationById(String databaseUrlId) {
        DatabaseUrl databaseUrl = databaseUrlService.findById(databaseUrlId);
        final String migrationUri = new StringBuilder().append(MIGRATION_URI_PREFIX)
                .append(changelogMaster)
                .toString();
        DataSource datasource = DataSourceBuilder.create().url(databaseUrl.getUrl()).build();
        try (Connection connection = datasource.getConnection()) { // Here should be already changed datasource
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(migrationUri, new ClassLoaderResourceAccessor(), database);
            liquibase.update("");
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    @Override
    public void applyMigrationByUrl(String databaseUrl) {
        final String migrationUri = new StringBuilder().append(MIGRATION_URI_PREFIX)
                .append(changelogMaster)
                .toString();
        DataSource datasource = DataSourceBuilder.create().url(databaseUrl).build();
        try (Connection connection = datasource.getConnection()) { // Here should be already changed datasource
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(migrationUri, new ClassLoaderResourceAccessor(), database);
            liquibase.update("");
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
}
