package com.haulmont.testtask.jpa.doctor.view;

import com.haulmont.testtask.jpa.RepositoryWithSoftFilter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorHumanizedRepository extends
  CrudRepository<DoctorHumanized, Long>, RepositoryWithSoftFilter<DoctorHumanized> {
}
