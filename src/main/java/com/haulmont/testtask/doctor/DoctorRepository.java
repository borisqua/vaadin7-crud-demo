package com.haulmont.testtask.doctor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long>/*CrudRepository<Doctor, Long>*/ {
/*  Doctor findByName(String name);
  List<Doctor> findByName(String name);
  List<Doctor> findBySurname(String surname);
  List<Doctor> findByNameAndSurname(String name, String surname);
  List<Doctor> findBySpecialization(String specialization);*/
  
}
