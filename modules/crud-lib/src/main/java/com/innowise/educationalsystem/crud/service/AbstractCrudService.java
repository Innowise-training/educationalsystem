package com.innowise.educationalsystem.crud.service;

import com.innowise.educationalsystem.crud.repository.CrudRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractCrudService<T, ID, R extends CrudRepository<T, ID>> implements CrudService<T, ID> {
    protected final R crudRepository;

    @Override
    public <S extends T> S save(S entity) {
        return crudRepository.save(entity);
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return crudRepository.saveAll(entities);
    }

    @Override
    public Optional<T> findById(ID id) {
        return crudRepository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return crudRepository.findAll();
    }

    @Override
    public List<T> findAllById(Iterable<ID> idIterable) {
        return crudRepository.findAllById(idIterable);
    }

    @Override
    public void deleteById(ID id) {
        crudRepository.deleteById(id);
    }

    @Override
    public void delete(T entity) {
        crudRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        crudRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        crudRepository.deleteAll();
    }
}
