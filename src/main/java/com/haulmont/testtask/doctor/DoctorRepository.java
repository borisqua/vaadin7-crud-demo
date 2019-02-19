package com.haulmont.testtask.doctor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long>/*CrudRepository<Doctor, Long>*/ {}
