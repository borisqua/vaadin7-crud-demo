package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.jpa.doctor.Doctor;
import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.ui.GridForm;
import com.haulmont.testtask.ui.ModalDialog;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.UI;

@SuppressWarnings("unused")
@SpringView
@Title("Haulmont test app / Врачи")
@Theme("valo")
public class DoctorsGrid extends GridForm<Doctor> {
  
  private final DoctorRepository doctorRepository;
  
  private Long doctorId = -1L;
  private Doctor doctor;
  private ModalDialog editDialog;
  private DeleteDoctorDialog deleteDialog;
  
  public DoctorsGrid(DoctorRepository doctorRepository) {
    
    super(Doctor.class, "Врачи", doctorRepository);
    
    this.doctorRepository = doctorRepository;
    
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          editButton.setEnabled(true);
          deleteButton.setEnabled(true);
        } else {
          this.doctorId = -1L;
          editButton.setEnabled(false);
          deleteButton.setEnabled(false);
        }
      }
    );
    
    setColumns(/*"id", */"surname", "name", "patronymic", "specialization");
    setColumnCaptions(/*"id", */"Фамилия", "Имя", "Отчество", "Телефон");
    
    addButton.addClickListener(e -> {
      grid.deselectAll();
      editDialog = new EditDoctorDialog("Врач", UI.getCurrent(), this, "Добавить запись",
        null, this.doctorRepository);
      editDialog.open();
    });
    editButton.addClickListener(e -> {
      this.doctorId= ((Doctor) (grid.getSelectionModel()).getSelectedRows().toArray()[0]).getId();
      this.doctorRepository.findById(this.doctorId).ifPresent(p -> this.doctor = p);
      editDialog = new EditDoctorDialog("Врач", UI.getCurrent(), this, "Изменить запись",
        this.doctor, this.doctorRepository);
      editDialog.open();
    });
    deleteButton.addClickListener(e -> {
      this.doctorId= ((Doctor) (grid.getSelectionModel()).getSelectedRows().toArray()[0]).getId();
      this.doctorRepository.findById(this.doctorId).ifPresent(p -> this.doctor = p);
      deleteDialog = new DeleteDoctorDialog("", UI.getCurrent(), this,
        "Удалить выбранную запись?", this.doctor, this.doctorRepository);
      deleteDialog.open();
    });
    
  }
  
}


