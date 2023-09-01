package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.crud.service.AbstractCrudService;
import com.innowise.educationalsystem.document.Type;
import com.innowise.educationalsystem.repository.TypeRepository;
import com.innowise.educationalsystem.service.TypeService;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl extends AbstractCrudService<Type, String, TypeRepository> implements TypeService {
    public TypeServiceImpl(TypeRepository crudRepository) {
        super(crudRepository);
    }
}
