package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.crud.service.AbstractCrudService;
import com.innowise.educationalsystem.entity.Task;
import com.innowise.educationalsystem.repository.TaskRepository;
import com.innowise.educationalsystem.service.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends AbstractCrudService<Task<?, ?, ?>, String, TaskRepository> implements TaskService {

    public TaskServiceImpl(TaskRepository crudRepository) {
        super(crudRepository);
    }
}
