package com.innowise.educationalsystem.controller;

import com.innowise.educationalsystem.document.Task;
import com.innowise.educationalsystem.dto.TaskDto;
import com.innowise.educationalsystem.mapper.TaskMapper;
import com.innowise.educationalsystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/type")
public class TypeController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task<?, ?> createTask(@RequestBody TaskDto<?, ?> taskDto) {
        //return taskService.save(typeFactory.provideTask(typeDto));
        Task<?, ?> task = taskMapper.map(taskDto);
        return task;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Task<?, ?>> getTasks() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task<?, ?> getTask(@PathVariable("id") String id) {
        return taskService.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}