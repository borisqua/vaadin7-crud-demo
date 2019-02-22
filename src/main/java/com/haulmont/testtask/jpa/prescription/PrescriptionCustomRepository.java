package com.haulmont.testtask.jpa.prescription;

import java.util.List;

public interface PrescriptionCustomRepository {
  
  List<Prescription> findByCustomCriteria(Long patientId, String priority, String  pattern);
  
}
