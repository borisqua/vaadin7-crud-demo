package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.prescription.Prescription;
import com.haulmont.testtask.jpa.prescription.PrescriptionRepository;
import com.haulmont.testtask.ui.ModalForm;
import com.vaadin.ui.UI;

class PrescriptionForm extends ModalForm<Prescription> {
  PrescriptionForm(String caption, UI hostUI, Prescription prescription, PrescriptionRepository prescriptionRepository) {
    super(caption, hostUI, prescription, prescriptionRepository);
  
  }
}
