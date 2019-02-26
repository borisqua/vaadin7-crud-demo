package com.haulmont.testtask.jpa.doctor;

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
  
  @Query(value = "SELECT id+': '+TRIM(BOTH FROM+TRIM(BOTH FROM TRIM(BOTH FROM name)+' '+patronymic)+' '+ surname) as fullName FROM doctors", nativeQuery = true)
  List<String> getAllDoctorsFullNames(/*@Param("pattern") String pattern*/);
  
  @Query(value = "select distinct name from doctors;", nativeQuery = true)
  List<String> getAllDoctorsNames();
  
  @Query(value = "select distinct surname from doctors;", nativeQuery = true)
  List<String> getAllDoctorsSurnames();
  
  @Query(value = "select distinct specialization from doctors;", nativeQuery = true)
  List<String> getAllDoctorsSpecs();
  
}
