package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.jpa.doctor.view.DoctorHumanized;
import com.haulmont.testtask.jpa.doctor.view.DoctorHumanizedRepository;
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
public class DoctorsGrid extends GridForm<DoctorHumanized> {
  
  private Long doctorId = -1L;
  
  public DoctorsGrid(DoctorHumanizedRepository doctorHumanizedRepository,
                     DoctorRepository doctorRepository) {
    
    super(DoctorHumanized.class, "Doctors", doctorHumanizedRepository);
    
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          doctorId = ((DoctorHumanized) event.getSelected().toArray()[0]).getId();
          Notification.show(doctorId.toString(), Notification.Type.TRAY_NOTIFICATION);
        }
      }
    );
    
    setColumns(/*"id", */"surname", "name", "patronymic", "specialization");
    setColumnCaptions(/*"id", */"Фамилия", "Имя", "Отчество", "Специализация");
    
    DoctorDialog doctorDialog = new DoctorDialog("Врач", UI.getCurrent(), doctorId, doctorRepository);
    
    final Button.ClickListener clickListener = e -> doctorDialog.open();
    addButton.addClickListener(clickListener);
    editButton.addClickListener(clickListener);
    deleteButton.addClickListener(clickListener);
    
  }
  
}
