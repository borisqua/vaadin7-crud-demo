package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.jpa.doctor.Doctor;
import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.ui.PersonForm;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

@SuppressWarnings("unused")
class EditDoctorDialog extends PersonForm {
  
  protected final TextField specialization = new TextField("Специализация");
  
  EditDoctorDialog(String caption, UI hostUI, Doctor doctor, DoctorRepository repository) {
    
    super(caption, hostUI);
    
  }
}