package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.jpa.doctor.Doctor;
import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.ui.GridForm;
import com.haulmont.testtask.ui.ModalDialog;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SuppressWarnings("unused")
@SpringView
@Title("Haulmont test app / Doctors")
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
  
    editDialog = new EditDoctorDialog("Врач", UI.getCurrent(), this,
      null, this.doctorRepository);
    deleteDialog = new DeleteDoctorDialog("", UI.getCurrent(), this,
      "Удалить выбранную запись?", null, this.doctorRepository);
  
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          doctor = (Doctor) event.getSelected().toArray()[0];
          doctorId = doctor.getId();
          Notification.show(doctor.toString(), Notification.Type.TRAY_NOTIFICATION);
          editDialog = new EditDoctorDialog("Врач", UI.getCurrent(), this,
            doctor, this.doctorRepository);
          deleteDialog = new DeleteDoctorDialog("", UI.getCurrent(), this,
            "Удалить выбранную запись?", doctor, this.doctorRepository);
          editButton.setEnabled(true);
          deleteButton.setEnabled(true);
        } else {
          editDialog = new EditDoctorDialog("Врач", UI.getCurrent(), this,
            null, this.doctorRepository);
          deleteDialog = new DeleteDoctorDialog("", UI.getCurrent(), this,
            "Удалить выбранную запись?", null, this.doctorRepository);
          editButton.setEnabled(false);
          deleteButton.setEnabled(false);
        }
      }
    );
    
    setColumns(/*"id", */"surname", "name", "patronymic", "specialization");
    setColumnCaptions(/*"id", */"Фамилия", "Имя", "Отчество", "Специализация");
    
    EditDoctorDialog editDoctorDialog = new EditDoctorDialog("Врач", UI.getCurrent(), this, doctor, doctorRepository);
    
    addButton.addClickListener(e -> editDialog.open());
    editButton.addClickListener(e -> editDialog.open());
    deleteButton.addClickListener(e -> deleteDialog.open());
    
  }
  
}

