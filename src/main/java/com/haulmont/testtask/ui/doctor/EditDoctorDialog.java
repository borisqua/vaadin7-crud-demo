package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.jpa.doctor.Doctor;
import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.ui.PersonForm;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;

@SuppressWarnings("unused")
class EditDoctorDialog extends PersonForm {
  
  private TextField specializationField;
  private DoctorsGrid doctorsGrid;
  
  private String name;
  private String surname;
  private String patronymic;
  private String specialization;
  private Doctor doctor;
  private DoctorRepository doctorRepository;
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  EditDoctorDialog(String caption, UI hostUI, DoctorsGrid doctorsGrid, String titleString,
                   @Nullable Doctor doctor,
                   @Nullable DoctorRepository doctorRepository) {
    
    super(caption, hostUI, titleString);
    
    if (doctorRepository == null) {
      return;
    }
    
    this.doctorsGrid = doctorsGrid;
    
    this.specializationField = prepareTextField("Специализация", "", true);
  
    this.doctor = doctor;
    this.doctorRepository = doctorRepository;
    
    form.addComponent(this.specializationField);
    
    if (this.doctor != null) {
      this.nameField.setValue(this.doctor.getName());
      this.surnameField.setValue(this.doctor.getSurname());
      this.patronymicField.setValue(this.doctor.getPatronymic());
      this.specializationField.setValue(this.doctor.getSpecialization());
    } else {
      this.nameField.setValue("");
      this.surnameField.setValue("");
      this.patronymicField.setValue("");
      this.specializationField.setValue("");
    }
    
    getOKButton().addClickListener(event -> {
      if (formIsValid()) {
        try {
          name = this.nameField.getValue();
          surname = this.surnameField.getValue();
          patronymic = this.patronymicField.getValue();
          specialization = this.specializationField.getValue();
          if (this.doctor == null) {
            this.doctor = new Doctor(name, surname, patronymic, specialization);
            this.doctorRepository.save(this.doctor);
          } else {
            this.doctor.setName(name);
            this.doctor.setSurname(surname);
            this.doctor.setPatronymic(patronymic);
            this.doctor.setSpecialization(specialization);
            this.doctorRepository.save(this.doctor);
          }
          this.doctorsGrid.updateList(Doctor.class);
        } catch (DataIntegrityViolationException dataIntegrityError) {
          LOGGER.debug("HaulmontLOG4J2: DATA INTEGRITY ERROR WHILE SAVING DOCTOR ENTITY -> {}", dataIntegrityError);
          dataIntegrityError.printStackTrace();
        } catch (NumberFormatException e) {
          LOGGER.debug("HaulmontLOG4J2:  UNKNOWN ERROR WHILE SAVING PATIENT DOCTOR -> {}", e);
          e.printStackTrace();
        } finally {
          close();
        }
      } else {
        Notification.show("Данные введены не полностью или неверный формат данных", Notification.Type./*TRAY_NOTIFICATION*/TRAY_NOTIFICATION);
      }
    });
    
    addCloseListener(event -> {
      this.specializationField.setValidationVisible(false);
      this.specializationField.setValue("");
      this.doctor = null;
      isOpened = false;
    });
    
  }
  
  @Override
  protected void setValidationVisibility(Boolean isVisible) {
    super.setValidationVisibility(isVisible);
    this.specializationField.setValidationVisible(isVisible);
  }
  
  @Override
  protected Boolean formIsValid() {
    setValidationVisibility(true);
    Boolean personsFieldsValid = super.formIsValid();
    return personsFieldsValid && this.specializationField.isValid();
  }
  
}
