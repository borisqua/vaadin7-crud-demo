package com.haulmont.testtask.jpa.prescription;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface PrescriptionRepository extends CrudRepository<Prescription, Long> {
  @Query(value =
    "SELECT TRIM(BOTH FROM+TRIM(BOTH FROM TRIM(BOTH FROM name)+' '+patronymic)+' '+ surname) AS doctor, COUNT(*) AS count " +
    "FROM prescriptions AS p" +
      "INNER JOIN doctors AS d ON d.id = p.doctorId" +
    "GROUP BY d.id", nativeQuery = true)
  Map<String, Integer> getPrescriptionsPerDoctorReport();
}
