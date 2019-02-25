package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.jpa.doctor.Doctor;
import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.ui.GridForm;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SpringView
@Title("Haulmont test app / Doctors")
@Theme("valo")
public class DoctorsGrid extends GridForm<Doctor> {
  
  private Long doctorId = -1L;
  private Doctor doctor;
  
  public DoctorsGrid(DoctorRepository doctorRepository) {
    super(Doctor.class, "Врачи", doctorRepository);
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          doctor = (Doctor) event.getSelected().toArray()[0];
          doctorId = doctor.getId();
          Notification.show(doctor.toString(), Notification.Type.TRAY_NOTIFICATION);
          editButton.setEnabled(true);
          deleteButton.setEnabled(true);
        } else {
          editButton.setEnabled(false);
          deleteButton.setEnabled(false);
        }
      }
    );
    
    setColumns(/*"id", */"surname", "name", "patronymic", "specialization");
    setColumnCaptions(/*"id", */"Фамилия", "Имя", "Отчество", "Специализация");
    
    DoctorDialog doctorDialog = new DoctorDialog("Врач", UI.getCurrent(), doctorId, doctorRepository);
  
    if(doctorId > 0){
      doctorRepository.findById(doctorId).ifPresent(e->doctor = e);
    }
    
    addButton.addClickListener(e -> doctorDialog.open());
    editButton.addClickListener(e -> {
      if(doctorId > 0){
        doctorDialog.open();
      }
    });
    deleteButton.addClickListener(e -> {
      if(doctorId > 0){
        doctorRepository.delete(doctor);
      }
    });
    
  }
  
}
