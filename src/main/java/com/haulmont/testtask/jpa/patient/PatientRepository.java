package com.haulmont.testtask.jpa.patient;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

//  List<Patient> findByName(String text);
//  List<Patient> findBySurname(String text);
  
  @Query(value = "SELECT id+': '+TRIM(BOTH FROM TRIM(BOTH FROM TRIM(BOTH FROM name)+' '+patronymic)+' '+ surname) as fullName FROM patients", nativeQuery = true)
  List<String> getAllPatientsFullNames(/*@Param("pattern") String pattern*/);
  
  @Query(value = "select distinct name from patients;", nativeQuery = true)
  List<String> getAllPatientsNames();
  
  @Query(value = "select distinct surname from patients;", nativeQuery = true)
  List<String> getAllPatientsSurnames();
  
}
