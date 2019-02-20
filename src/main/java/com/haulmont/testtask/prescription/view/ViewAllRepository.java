package com.haulmont.testtask.prescription.view;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewAllRepository extends CrudRepository<ViewAll, Long>, ViewAllCustomRepository {
}
