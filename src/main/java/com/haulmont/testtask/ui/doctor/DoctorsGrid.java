package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.jpa.doctor.Doctor;
import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.ui.GridForm;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SpringView
@Title("Haulmont test app / Doctors")
@Theme("valo")
public class DoctorsGrid extends GridForm<Doctor> {
  
  public DoctorsGrid(DoctorRepository doctorRepository) {
    super(Doctor.class, "Doctors", doctorRepository);
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          entity = (Doctor) event.getSelected().toArray()[0];
          Notification.show(entity.toString(), Notification.Type.TRAY_NOTIFICATION);
        }
      }
    );
    
    setColumns(/*"id", */"surname", "name", "patronymic", "specialization");
    setColumnCaptions(/*"id", */"Фамилия", "Имя", "Отчество", "Специализация");
    
    DoctorDialog doctorDialog = new DoctorDialog("Врач", UI.getCurrent(), entity, doctorRepository);
    
    final Button.ClickListener clickListener = e -> doctorDialog.open();
    
    addButton.addClickListener(clickListener);
    editButton.addClickListener(clickListener);
    deleteButton.addClickListener(clickListener);
    
  }
  
}
