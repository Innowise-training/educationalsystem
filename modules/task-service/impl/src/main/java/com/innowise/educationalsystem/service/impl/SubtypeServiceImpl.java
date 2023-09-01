package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.crud.service.AbstractCrudService;
import com.innowise.educationalsystem.document.Subtype;
import com.innowise.educationalsystem.repository.SubtypeRepository;
import com.innowise.educationalsystem.service.SubtypeService;

public class SubtypeServiceImpl extends AbstractCrudService<Subtype, String, SubtypeRepository> implements SubtypeService {
    public SubtypeServiceImpl(SubtypeRepository crudRepository) {
        super(crudRepository);
    }
}
