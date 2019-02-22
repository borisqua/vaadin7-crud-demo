package com.haulmont.testtask.jpa.prescription.view;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewAllRepository extends CrudRepository<ViewAll, Long>, ViewAllCustomRepository {
}
