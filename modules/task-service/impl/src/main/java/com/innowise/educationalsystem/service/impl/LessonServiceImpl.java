package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.crud.service.AbstractCrudService;
import com.innowise.educationalsystem.entity.Lesson;
import com.innowise.educationalsystem.repository.LessonRepository;
import com.innowise.educationalsystem.service.LessonService;

public class LessonServiceImpl extends AbstractCrudService<Lesson, String, LessonRepository> implements LessonService {
    public LessonServiceImpl(LessonRepository crudRepository) {
        super(crudRepository);
    }
}
