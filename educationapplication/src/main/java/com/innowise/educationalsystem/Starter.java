package com.innowise.educationalsystem;

import com.innowise.educationalsystem.domain.DatabaseUrl;
import com.innowise.educationalsystem.repository.DatabaseUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

//@Configuration
@RequiredArgsConstructor
public class Starter implements CommandLineRunner {
    private final DatabaseUrlRepository databaseUrlRepository;

    @Override
    public void run(String... args) throws Exception {
        DatabaseUrl databaseUrl = new DatabaseUrl();
        databaseUrl.setId(UUID.randomUUID().toString());
        databaseUrl.setUrl("jdbc:postgresql://postgres:root@localhost:5432/courses?schema=sub1");
        databaseUrlRepository.saveAndFlush(databaseUrl);

    }
}
