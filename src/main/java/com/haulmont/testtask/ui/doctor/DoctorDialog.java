package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.jpa.doctor.Doctor;
import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.ui.PersonForm;
import com.vaadin.ui.UI;

class DoctorDialog extends PersonForm<Doctor> {
  DoctorDialog(String caption, UI hostUI, Long doctorId, DoctorRepository repository) {
    super(caption, hostUI, doctorId, repository);
  }
}
