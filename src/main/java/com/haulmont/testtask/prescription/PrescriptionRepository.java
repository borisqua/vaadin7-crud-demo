package com.haulmont.testtask.prescription;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface PrescriptionRepository extends CrudRepository<Prescription, Long>/*CrudRepository<Prescription, Long>*/ {
  List<Prescription> findByDescription(String pattern);
  List<Prescription> findByPatientId(Long id);
  List<Prescription> findByPriority(String priority);
  
  List<Prescription> findByDescriptionLike(String pattern);
  List<Prescription> findByPatientIdAndPriority(Long patientId, String priority);
  List<Prescription> findByPatientIdAndDescriptionContaining(Long patientId, String pattern);
  List<Prescription> findByPriorityAndDescriptionContaining(Long patientId, String pattern);
  List<Prescription> findByPatientIdAndPriorityAndDescriptionContaining(Long patientId, String priority, String pattern);
  
  Long countByDoctorId(Long doctorId);
  Long countByDoctorIdAndPriority(Long doctorId, String priority);
  Long countByDoctorIdAndDescriptionIsLike(Long doctorId, String pattern);
  Long countByDoctorIdAndPriorityAndDescriptionIsLike(Long doctorId, String priority, String pattern);
}
