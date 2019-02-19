package com.haulmont.testtask.prescription;

import java.util.List;

public interface CustomPrescriptionRepository {
  
  List<Prescription> findByCustomCriteria(Long patientId, String priority, String  pattern);
  
}
