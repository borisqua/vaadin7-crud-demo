package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.jpa.doctor.Doctor;
import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.ui.PersonForm;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

@SuppressWarnings("unused")
class EditDoctorDialog extends PersonForm {
  
  private final TextField specializationField = new TextField("Специализация");
  private final DoctorsGrid doctorsGrid;
  
  private String name;
  private String surname;
  private String patronymic;
  private String specialization;
  private Doctor doctor;
  private DoctorRepository doctorRepository;
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  EditDoctorDialog(String caption, UI hostUI, DoctorsGrid doctorsGrid,
                   Doctor doctor, DoctorRepository doctorRepository) {
    super(caption, hostUI);
    this.doctorsGrid = doctorsGrid;
    this.doctor = doctor;
    this.doctorRepository = doctorRepository;
  
    form.addComponent(specializationField);
    
    if (this.doctor != null) {
      nameField.setValue(this.doctor.getName());
      surnameField.setValue(this.doctor.getSurname());
      patronymicField.setValue(this.doctor.getPatronymic());
      specializationField.setValue(this.doctor.getSpecialization());
    }
    getOKButton().addClickListener(event -> {
      try {
        name = nameField.getValue();
        surname = surnameField.getValue();
        patronymic = patronymicField.getValue();
        specialization = specializationField.getValue();
        if (this.doctor == null) {
          this.doctor = new Doctor(name, surname, patronymic, specialization);
        } else {
          this.doctor.setName(name);
          this.doctor.setSurname(surname);
          this.doctor.setPatronymic(patronymic);
          this.doctor.setSpecialization(specialization);
          this.doctorRepository.save(this.doctor);
        }
        this.doctorsGrid.updateList(Doctor.class);
      } catch (DataIntegrityViolationException dataIntegrityError) {
        LOGGER.debug("HaulmontLOG4J2: DATA INTEGRITY ERROR WHILE SAVING PATIENT ENTITY -> {}", dataIntegrityError);
        dataIntegrityError.printStackTrace();
      } catch (NumberFormatException e) {
        LOGGER.debug("HaulmontLOG4J2:  UNKNOWN ERROR WHILE SAVING PATIENT ENTITY -> {}", e);
        e.printStackTrace();
      }
    });
  }
}
