package com.haulmont.testtask.jpa.prescription.view;

import com.haulmont.testtask.jpa.RepositoryWithSoftFilter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionHumanizedRepository extends
  CrudRepository<PrescriptionHumanized, Long>, RepositoryWithSoftFilter<PrescriptionHumanized> {
}
