package com.innowise.educationalsystem.factory.producer;

import com.innowise.educationalsystem.dto.temp.TypeDto;
import com.innowise.educationalsystem.document.Task;

public interface TypeProducer {
    Task<?, ?, ?> produce(TypeDto typeDto);

    boolean canProduce(String name);
}
