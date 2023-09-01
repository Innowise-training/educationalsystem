package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.crud.service.AbstractCrudService;
import com.innowise.educationalsystem.entity.TaskType;
import com.innowise.educationalsystem.repository.TaskTypeRepository;
import com.innowise.educationalsystem.service.TaskTypeService;

public class TaskTypeServiceImpl extends AbstractCrudService<TaskType, String, TaskTypeRepository> implements TaskTypeService {
    public TaskTypeServiceImpl(TaskTypeRepository crudRepository) {
        super(crudRepository);
    }
}
