package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.jpa.patient.Patient;
import com.haulmont.testtask.ui.DeleteDialog;
import com.vaadin.ui.UI;
import org.springframework.data.repository.CrudRepository;

class DeletePatientDialog extends DeleteDialog<Patient> {
  private PatientsGrid patientsGrid;
  
  DeletePatientDialog(String caption, UI hostUI, PatientsGrid patientsGrid,
                      String labelString, Patient entity, CrudRepository<Patient, Long> repository) {
    super(caption, hostUI, labelString, entity, repository);
    
    this.patientsGrid = patientsGrid;
    
    getOKButton().addClickListener(e -> this.patientsGrid.updateList(Patient.class));
  }
}
