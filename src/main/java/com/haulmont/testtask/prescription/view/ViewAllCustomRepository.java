package com.haulmont.testtask.prescription.view;

import java.util.List;

public interface ViewAllCustomRepository {
  
  List<ViewAll> findByCustomCriteria(String patient, String priority, String prescriptionPattern);
  
}
