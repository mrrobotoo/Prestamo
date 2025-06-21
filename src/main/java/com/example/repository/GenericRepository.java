package com.example.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {
	T save(T entity);
	Optional<T> findById(ID id);
	List<T> findAll();
	void update(T entity, ID id);
	void deleteById(ID id);
}
