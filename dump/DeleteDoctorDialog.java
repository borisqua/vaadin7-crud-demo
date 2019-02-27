package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.jpa.doctor.Doctor;
import com.haulmont.testtask.ui.DeleteDialog;
import com.vaadin.ui.UI;
import org.springframework.data.repository.CrudRepository;

class DeleteDoctorDialog extends DeleteDialog<Doctor> {
  private DoctorsGrid doctorsGrid;
  
  DeleteDoctorDialog(String caption, UI hostUI, DoctorsGrid doctorsGrid,
                     String labelString, Doctor entity, CrudRepository<Doctor, Long> repository) {
    super(caption, hostUI, labelString, entity, repository);
    
    this.doctorsGrid = doctorsGrid;
    
    getOKButton().addClickListener(e -> this.doctorsGrid.updateList(Doctor.class));
  }
}
