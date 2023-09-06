package com.innowise.educationalsystem.factory.producer.impl;

import com.innowise.educationalsystem.dto.temp.TypeDto;
import com.innowise.educationalsystem.document.subtype.ordering.OrderingSubtype;
import com.innowise.educationalsystem.document.task.OrderingTask;
import com.innowise.educationalsystem.enums.TypeName;
import com.innowise.educationalsystem.factory.producer.TypeProducer;
import com.innowise.educationalsystem.factory.subtype.SubtypeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderingTypeProducer implements TypeProducer {
    @Qualifier("orderingSubtypeFactory")
    private final SubtypeFactory orderingSubtypeFactory;

    @Override
    public OrderingTask<? extends OrderingSubtype<?>> produce(TypeDto typeDto) {
        return new OrderingTask<>(typeDto, orderingSubtypeFactory);
    }

    @Override
    public boolean canProduce(String name) {
        return name.equalsIgnoreCase(TypeName.ORDERING.name());
    }
}
