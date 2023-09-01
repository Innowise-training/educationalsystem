package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.crud.service.AbstractCrudService;
import com.innowise.educationalsystem.entity.TaskSubtype;
import com.innowise.educationalsystem.repository.TaskSubtypeRepository;
import com.innowise.educationalsystem.service.TaskSubtypeService;

public class TaskSubtypeServiceImpl extends AbstractCrudService<TaskSubtype, String, TaskSubtypeRepository> implements TaskSubtypeService {
    public TaskSubtypeServiceImpl(TaskSubtypeRepository crudRepository) {
        super(crudRepository);
    }
}
