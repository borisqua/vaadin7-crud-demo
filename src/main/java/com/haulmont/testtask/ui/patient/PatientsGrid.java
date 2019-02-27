package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.jpa.patient.Patient;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.ui.GridForm;
import com.haulmont.testtask.ui.ModalDialog;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SuppressWarnings("unused")
@SpringView
@Title("Haulmont test app / Patients")
@Theme("valo")
public class PatientsGrid extends GridForm<Patient> {
  
  private final PatientRepository patientRepository;
  
  private Long patientId = -1L;
  private Patient patient;
  private ModalDialog editDialog;
  private DeletePatientDialog deleteDialog;
  
  public PatientsGrid(PatientRepository patientRepository) {
  
    super(Patient.class, "Пациенты", patientRepository);
    
    this.patientRepository = patientRepository;
  
    editDialog = new EditPatientDialog("ПациентПациент", UI.getCurrent(), this,
      null, this.patientRepository);
    deleteDialog = new DeletePatientDialog("", UI.getCurrent(), this,
      "Удалить выбранную запись?", null, this.patientRepository);
  
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          patient = (Patient) event.getSelected().toArray()[0];
          patientId = patient.getId();
          Notification.show(patient.toString(), Notification.Type.TRAY_NOTIFICATION);
          editDialog = new EditPatientDialog("Пациент", UI.getCurrent(), this,
            patient, this.patientRepository);
          deleteDialog = new DeletePatientDialog("", UI.getCurrent(), this,
            "Удалить выбранную запись?", patient, this.patientRepository);
          editButton.setEnabled(true);
          deleteButton.setEnabled(true);
        } else {
          editDialog = new EditPatientDialog("Пациент", UI.getCurrent(), this,
            null, this.patientRepository);
          deleteDialog = new DeletePatientDialog("", UI.getCurrent(), this,
            "Удалить выбранную запись?", null, this.patientRepository);
          editButton.setEnabled(false);
          deleteButton.setEnabled(false);
        }
      }
    );
    
    setColumns(/*"id", */"surname", "name", "patronymic", "phone");
    setColumnCaptions(/*"id", */"Фамилия", "Имя", "Отчество", "Телефон");
    
    EditPatientDialog editPatientDialog = new EditPatientDialog("Пациент", UI.getCurrent(), this, patient, patientRepository);
    
    addButton.addClickListener(e -> editDialog.open());
    editButton.addClickListener(e -> editDialog.open());
    deleteButton.addClickListener(e -> deleteDialog.open());
    
  }
  
}

