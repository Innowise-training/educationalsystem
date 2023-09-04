package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.domain.DatabaseUrl;
import com.innowise.educationalsystem.repository.DatabaseUrlRepository;
import com.innowise.educationalsystem.service.DatabaseUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseUrlServiceImpl implements DatabaseUrlService {
    private final DatabaseUrlRepository databaseUrlRepository;

    @Override
    @Transactional(readOnly = true)
    public DatabaseUrl findById(String id) {
        return databaseUrlRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DatabaseUrl> findAll() {
        return databaseUrlRepository.findAll();
    }
}
