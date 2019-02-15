package com.haulmont.testtask;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
  List<Doctor> findByName(String name);
  List<Doctor> findBySurname(String surname);
  List<Doctor> findByFullNameString(String name, String surname);
  List<Doctor> findBySpecialization(String specialization);
  List<Doctor> findByPriority(Priority priority);
}
