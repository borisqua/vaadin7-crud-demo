package com.haulmont.testtask;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends CrudRepository<Prescription, Long> {
  List<Prescription> findByDoctorId(Long id);
  List<Prescription> findByPatientId(Long id);
  List<Prescription> findByDescription(String pattern);
  List<Prescription> findByPriority(Prescription.Priority priority);
}
