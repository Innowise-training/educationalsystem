package com.innowise.educationalsystem.factory;

import com.innowise.educationalsystem.dto.TypeDto;
import com.innowise.educationalsystem.document.Task;
import com.innowise.educationalsystem.exception.TypeCreationException;
import com.innowise.educationalsystem.factory.producer.TypeProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TypeFactory {
    private final List<TypeProducer> typeProducers;

    public Task<?, ?, ?> provideTask(TypeDto typeDto) {

        return typeProducers.stream()
                .filter(p -> p.canProduce(typeDto.getName()))
                .findFirst()
                .orElseThrow(() -> new TypeCreationException("Unknown type " + typeDto.getName()))
                .produce(typeDto);
    }
}
