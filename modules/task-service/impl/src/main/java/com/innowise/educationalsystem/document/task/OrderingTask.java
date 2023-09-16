package com.innowise.educationalsystem.document.task;

import com.innowise.educationalsystem.document.Task;
import com.innowise.educationalsystem.document.subtype.ordering.OrderingSubtype;
import com.innowise.educationalsystem.dto.temp.TypeDto;
import com.innowise.educationalsystem.factory.subtype.SubtypeFactory;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@TypeAlias("Ordering")
@Document(collection = "tasks")
public class OrderingTask extends Task<Set<String>, List<String>> {
    public OrderingTask() {
        super();
    }

    public OrderingTask(TypeDto typeDto, SubtypeFactory subtypeFactory) {
        super(typeDto, subtypeFactory);
    }
}
