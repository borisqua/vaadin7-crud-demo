package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.prescription.Prescription;
import com.haulmont.testtask.jpa.prescription.PrescriptionRepository;
import com.haulmont.testtask.ui.ModalForm;
import com.vaadin.ui.UI;

class PrescriptionDialog extends ModalForm<Prescription> {
  PrescriptionDialog(String caption, UI hostUI, Long prescriptionId, PrescriptionRepository prescriptionRepository) {
    super(caption, hostUI, prescriptionId, prescriptionRepository);
  
  }
}
