package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.jpa.patient.Patient;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.ui.PersonForm;
import com.vaadin.ui.UI;

class PatientDialog extends PersonForm<Patient> {
  PatientDialog(String caption, UI hostUI, Patient doctor, PatientRepository repository) {
    super(caption, hostUI, doctor, repository);
  }
}
