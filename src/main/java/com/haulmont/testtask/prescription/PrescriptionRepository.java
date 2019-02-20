package com.haulmont.testtask.prescription;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends CrudRepository<Prescription, Long>, PrescriptionCustomRepository {
}
