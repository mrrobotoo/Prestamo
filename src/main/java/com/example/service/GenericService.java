package com.example.service;

import java.util.List;
import com.example.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GenericService<T, ID> {
    protected final GenericRepository<T, ID> repository;

    protected GenericService(GenericRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T create(T entity) {
        log.info("Creating entity: {}", entity);
        return repository.save(entity);
    }

    public T getById(ID id) {
    	log.info("Fetching entity with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with id " + id));
    }

    public List<T> getAll() {
    	log.info("Fetching all entities");
        return repository.findAll();
    }

    public void update(T entity, ID id) {
    	log.info("Updating entity with ID: {}", id);
        repository.update(entity, id);
    }

    public void delete(ID id) {
    	log.info("Deleting entity with ID: {}", id);
        repository.deleteById(id);
    }
}