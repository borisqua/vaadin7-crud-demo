package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.jpa.patient.Patient;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.ui.GridForm;
import com.haulmont.testtask.ui.ModalDialog;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.UI;

@SuppressWarnings("unused")
@SpringView
@Title("Haulmont test app / Пациенты")
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
    
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          editButton.setEnabled(true);
          deleteButton.setEnabled(true);
        } else {
          this.patientId = -1L;
          editButton.setEnabled(false);
          deleteButton.setEnabled(false);
        }
      }
    );
    
    setColumns(/*"id", */"surname", "name", "patronymic", "phone");
    setColumnCaptions(/*"id", */"Фамилия", "Имя", "Отчество", "Телефон");
    
    addButton.addClickListener(e -> {
      grid.deselectAll();
      editDialog = new EditPatientDialog("Пациент", UI.getCurrent(), this, "Добавить запись",
        null, this.patientRepository);
      editDialog.open();
    });
    editButton.addClickListener(e -> {
      this.patientId= ((Patient) (grid.getSelectionModel()).getSelectedRows().toArray()[0]).getId();
      this.patientRepository.findById(this.patientId).ifPresent(p -> this.patient = p);
      editDialog = new EditPatientDialog("Пациент", UI.getCurrent(), this, "Изменить запись",
        this.patient, this.patientRepository);
      editDialog.open();
    });
    deleteButton.addClickListener(e -> {
      this.patientId= ((Patient) (grid.getSelectionModel()).getSelectedRows().toArray()[0]).getId();
      this.patientRepository.findById(this.patientId).ifPresent(p -> this.patient = p);
      deleteDialog = new DeletePatientDialog("", UI.getCurrent(), this,
        "Удалить выбранную запись?", this.patient, this.patientRepository);
      deleteDialog.open();
    });
    
  }
  
}

