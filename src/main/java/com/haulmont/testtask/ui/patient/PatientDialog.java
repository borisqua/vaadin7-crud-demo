package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.jpa.patient.Patient;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.ui.PersonForm;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

@SuppressWarnings("unused")
class PatientDialog extends PersonForm {
  
  protected final TextField phone = new TextField("Телефон");
  
  PatientDialog(String caption, UI hostUI, Patient patient, PatientRepository repository) {
    super(caption, hostUI);
  }
}
