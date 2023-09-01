package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.crud.service.AbstractCrudService;
import com.innowise.educationalsystem.entity.LessonSection;
import com.innowise.educationalsystem.repository.LessonSectionRepository;
import com.innowise.educationalsystem.service.LessonSectionService;

public class LessonSectionServiceImpl extends AbstractCrudService<LessonSection, String, LessonSectionRepository> implements LessonSectionService {
    public LessonSectionServiceImpl(LessonSectionRepository crudRepository) {
        super(crudRepository);
    }
}
