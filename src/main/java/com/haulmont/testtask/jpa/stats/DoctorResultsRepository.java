package com.haulmont.testtask.jpa.stats;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface DoctorResultsRepository extends CrudRepository<DoctorResult, Long> {
}
