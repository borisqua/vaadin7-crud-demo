package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.jpa.patient.Patient;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.ui.GridForm;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SpringView
@Title("Haulmont test app / Patients")
@Theme("valo")
public class PatientsGrid extends GridForm<Patient> {
  
  private Long patientId = -1L;
  private Patient patient;
  
  public PatientsGrid(PatientRepository patientRepository) {
    super(Patient.class, "Пациенты", patientRepository);
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          patient = (Patient) event.getSelected().toArray()[0];
          patientId = patient.getId();
          Notification.show(patient.toString(), Notification.Type.TRAY_NOTIFICATION);
          editButton.setEnabled(true);
          deleteButton.setEnabled(true);
        } else {
          editButton.setEnabled(false);
          deleteButton.setEnabled(false);
        }
      }
    );
    
    setColumns(/*"id", */"surname", "name", "patronymic", "phone");
    setColumnCaptions(/*"id", */"Фамилия", "Имя", "Отчество", "Телефон");
    
    PatientDialog patientDialog = new PatientDialog("Пациент", UI.getCurrent(), patientId, patientRepository);
    
    final Button.ClickListener clickListener = e -> patientDialog.open();
    
    addButton.addClickListener(clickListener);
    editButton.addClickListener(clickListener);
    deleteButton.addClickListener(clickListener);
    
  }
  
}

