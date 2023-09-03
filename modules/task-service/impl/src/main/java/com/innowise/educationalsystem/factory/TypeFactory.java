package com.innowise.educationalsystem.factory;

import com.innowise.educationalsystem.dto.TypeDto;
import com.innowise.educationalsystem.entity.Task;
import com.innowise.educationalsystem.entity.TranslationTask;
import org.springframework.stereotype.Component;

@Component
public class TypeFactory {

    public Task<?, ?, ?> provideTask(TypeDto typeDto) {
        switch (typeDto.getName()) {
            case "trans": return new TranslationTask<>(typeDto);
        }

        throw new RuntimeException();
    }
}
