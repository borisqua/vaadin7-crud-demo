package com.haulmont.testtask.doctor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
  
  List<Doctor> findByName(String text);
  List<Doctor> findBySurname(String text);
  List<Doctor> findBySpecialization(String text);
  
  @Query(value = "select distinct name from doctors;", nativeQuery = true)
  List<String> getAllDoctorsNames();
  
  @Query(value = "select distinct surname from doctors;", nativeQuery = true)
  List<String> getAllDoctorsSurnames();

  @Query(value = "select distinct specialization from doctors;", nativeQuery = true)
  List<String> getAllDoctorsSpecs();
  
}
