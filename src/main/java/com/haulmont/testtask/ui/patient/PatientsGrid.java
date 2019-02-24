package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.jpa.patient.Patient;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.jpa.patient.view.PatientHumanized;
import com.haulmont.testtask.jpa.patient.view.PatientHumanizedRepository;
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
public class PatientsGrid extends GridForm<PatientHumanized> {
  
  private Long patientId = -1L;
  
  public PatientsGrid(PatientHumanizedRepository patientHumanizedRepository,
                      PatientRepository patientRepository) {
    
    super(PatientHumanized.class, "Patients", patientHumanizedRepository);
    
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          patientId = ((Patient) event.getSelected().toArray()[0]).getId();
          Notification.show(patientId.toString(), Notification.Type.TRAY_NOTIFICATION);
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

