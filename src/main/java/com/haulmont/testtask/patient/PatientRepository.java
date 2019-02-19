package com.haulmont.testtask.patient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends CrudRepository<Patient, Long>/*CrudRepository<Patient, Long>*/ {}
