package com.innowise.educationalsystem.unit.service.impl;

import com.innowise.educationalsystem.crud.service.AbstractCrudService;
import com.innowise.educationalsystem.unit.entity.Unit;
import com.innowise.educationalsystem.unit.repository.UnitRepository;
import com.innowise.educationalsystem.unit.service.UnitService;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl extends AbstractCrudService<Unit, String, UnitRepository> implements UnitService {
    public UnitServiceImpl(UnitRepository repository) {
        super(repository);
    }
}
