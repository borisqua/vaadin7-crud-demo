package com.haulmont.testtask.jpa.patient.view;

import com.haulmont.testtask.jpa.RepositoryWithSoftFilter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientHumanizedRepository extends
  CrudRepository<PatientHumanized, Long>, RepositoryWithSoftFilter<PatientHumanized> {
}

