package com.haulmont.testtask.patient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends CrudRepository<Patient, Long>/*CrudRepository<Patient, Long>*/ {
/*  List<Patient> findByName(String name);
  List<Patient> findBySurname(String surname);
  List<Patient> findByNameAndSurname(String name, String surname);
  List<Patient> findByPhone(String phone);*/
}
