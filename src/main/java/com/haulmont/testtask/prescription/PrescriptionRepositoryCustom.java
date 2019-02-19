package com.haulmont.testtask.prescription;

import java.util.List;

//@SuppressWarnings("unused")
public interface PrescriptionRepositoryCustom {
  List<Prescription> findByPatientIdAndPriorityAndDescription(Long patientId, String priority, String  pattern);
}
