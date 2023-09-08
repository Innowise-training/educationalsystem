package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.document.Task;
import com.innowise.educationalsystem.document.task.OrderingTask;
import com.innowise.educationalsystem.document.task.TranslationTask;
import com.innowise.educationalsystem.dto.TaskDto;
import com.innowise.educationalsystem.dto.task.OrderingDto;
import com.innowise.educationalsystem.dto.task.TranslationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SubtypeMapper.class})
public interface TaskMapper {
    OrderingTask map(OrderingDto taskDto);

    TranslationTask map(TranslationDto taskDto);

    default Task<?, ?> map(TaskDto<?, ?> taskDto) {
        if (taskDto instanceof OrderingDto) {
            return this.map((OrderingDto) taskDto);
        } else if (taskDto instanceof TranslationDto) {
            return this.map((TranslationDto) taskDto);
        }

        throw new RuntimeException("Such type was not found");
    }
}
