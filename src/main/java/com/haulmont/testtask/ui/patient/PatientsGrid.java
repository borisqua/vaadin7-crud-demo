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
  
  public PatientsGrid(PatientRepository patientRepository) {
    super(Patient.class, "Patients", patientRepository);
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          entity = (Patient) event.getSelected().toArray()[0];
          Notification.show(entity.toString(), Notification.Type.TRAY_NOTIFICATION);
        }
      }
    );
    
    setColumns(/*"id", */"surname", "name", "patronymic", "phone");
    setColumnCaptions(/*"id", */"Фамилия", "Имя", "Отчество", "Телефон");
    
    PatientDialog patientDialog = new PatientDialog("Пациент", UI.getCurrent(), entity, patientRepository);
  
    final Button.ClickListener clickListener = e -> patientDialog.open();
  
    addButton.addClickListener(clickListener);
    editButton.addClickListener(clickListener);
    deleteButton.addClickListener(clickListener);
  
  }
  
}

