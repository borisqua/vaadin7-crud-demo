package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.jpa.patient.Patient;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.ui.PersonForm;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;

@SuppressWarnings("unused")
class EditPatientDialog extends PersonForm {
  
  private TextField phoneField;
  private PatientsGrid patientsGrid;
  
  private String name;
  private String surname;
  private String patronymic;
  private String phone;
  private Patient patient;
  private PatientRepository patientRepository;
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  EditPatientDialog(String caption, UI hostUI, PatientsGrid patientsGrid, String titleString,
                   @Nullable Patient patient,
                   @Nullable PatientRepository patientRepository) {
    
    super(caption, hostUI, titleString);
    
    if (patientRepository == null) {
      return;
    }
    
    this.patientsGrid = patientsGrid;
    
    this.phoneField = prepareTextField("Телефон", "", true);
    
    this.patient = patient;
    this.patientRepository = patientRepository;
    
    form.addComponent(this.phoneField);
    
    if (this.patient != null) {
      this.nameField.setValue(this.patient.getName());
      this.surnameField.setValue(this.patient.getSurname());
      this.patronymicField.setValue(this.patient.getPatronymic());
      this.phoneField.setValue(this.patient.getPhone());
    } else {
      this.nameField.setValue("");
      this.surnameField.setValue("");
      this.patronymicField.setValue("");
      this.phoneField.setValue("");
    }
    
    getOKButton().addClickListener(event -> {
      if (formIsValid()) {
        try {
          name = this.nameField.getValue();
          surname = this.surnameField.getValue();
          patronymic = this.patronymicField.getValue();
          phone = this.phoneField.getValue();
          if (this.patient == null) {
            this.patient = new Patient(name, surname, patronymic, phone);
            this.patientRepository.save(this.patient);
          } else {
            this.patient.setName(name);
            this.patient.setSurname(surname);
            this.patient.setPatronymic(patronymic);
            this.patient.setPhone(phone);
            this.patientRepository.save(this.patient);
          }
          this.patientsGrid.updateList(Patient.class);
        } catch (DataIntegrityViolationException dataIntegrityError) {
          LOGGER.debug("HaulmontLOG4J2: DATA INTEGRITY ERROR WHILE SAVING PATIENT ENTITY -> {}", dataIntegrityError);
          dataIntegrityError.printStackTrace();
        } catch (NumberFormatException e) {
          LOGGER.debug("HaulmontLOG4J2:  UNKNOWN ERROR WHILE SAVING PATIENT ENTITY -> {}", e);
          e.printStackTrace();
        } finally {
          close();
        }
      } else {
        Notification.show("Данные введены не полностью или неверный формат данных", Notification.Type./*TRAY_NOTIFICATION*/TRAY_NOTIFICATION);
      }
    });
    
    addCloseListener(event -> {
      this.phoneField.setValidationVisible(false);
      this.phoneField.setValue("");
      this.patient = null;
      isOpened = false;
    });
    
  }
  
  @Override
  protected void setValidationVisibility(Boolean isVisible) {
    super.setValidationVisibility(isVisible);
    this.phoneField.setValidationVisible(isVisible);
  }
  
  @Override
  protected Boolean formIsValid() {
    setValidationVisibility(true);
    Boolean personsFieldsValid = super.formIsValid();
//    try {
//      phoneField.validate();
//    } catch (Validator.InvalidValueException e) {
//      Notification.show("Необходимо правильно заполнить поле 'Телефон'", Notification.Type.TRAY_NOTIFICATION);
//    }
    return personsFieldsValid && this.phoneField.isValid();
  }
  
}

