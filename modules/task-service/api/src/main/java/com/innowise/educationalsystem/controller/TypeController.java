package com.innowise.educationalsystem.controller;

import com.innowise.educationalsystem.dto.TypeDto;
import com.innowise.educationalsystem.entity.Task;
import com.innowise.educationalsystem.factory.TypeFactory;
import com.innowise.educationalsystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/type")
public class TypeController {
    private final TaskService taskService;
    private final TypeFactory typeFactory;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task<?, ?, ?> createTask(@RequestBody TypeDto typeDto) {
        Task<?, ?, ?> task = typeFactory.provideTask(typeDto);
        return taskService.save(task);
    }
}
