package com.haulmont.testtask.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository<T> extends CrudRepository<T, Long>, RepositoryWithSoftFilter<T> {
}
