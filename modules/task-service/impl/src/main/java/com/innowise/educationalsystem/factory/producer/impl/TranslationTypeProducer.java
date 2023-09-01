package com.innowise.educationalsystem.factory.producer.impl;

import com.innowise.educationalsystem.dto.TypeDto;
import com.innowise.educationalsystem.document.subtype.translation.TranslationSubtype;
import com.innowise.educationalsystem.document.task.TranslationTask;
import com.innowise.educationalsystem.enums.TypeName;
import com.innowise.educationalsystem.factory.producer.TypeProducer;
import com.innowise.educationalsystem.factory.subtype.SubtypeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TranslationTypeProducer implements TypeProducer {
    @Qualifier("translationSubtypeFactory")
    private final SubtypeFactory translationSubtypeFactory;

    public TranslationTask<? extends TranslationSubtype<?>> produce(TypeDto typeDto) {
        return new TranslationTask<>(typeDto, translationSubtypeFactory);
    }

    @Override
    public boolean canProduce(String name) {
        return name.equalsIgnoreCase(TypeName.TRANSLATION.name());
    }
}
