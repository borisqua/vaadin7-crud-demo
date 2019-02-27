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

@SuppressWarnings("unused")
class EditPatientDialog extends PersonForm {
  
  private final TextField phoneField = preparePhoneField("Телефон", true);
  private final PatientsGrid patientsGrid;
  
  private String name;
  private String surname;
  private String patronymic;
  private String phone;
  private Patient patient;
  private PatientRepository patientRepository;
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  EditPatientDialog(String caption, UI hostUI, PatientsGrid patientsGrid,
                    Patient patient, PatientRepository patientRepository) {
    super(caption, hostUI);
    this.patientsGrid = patientsGrid;
    this.patient = patient;
    this.patientRepository = patientRepository;
  
    form.addComponent(phoneField);
    
    if (this.patient != null) {
      nameField.setValue(this.patient.getName());
      surnameField.setValue(this.patient.getSurname());
      patronymicField.setValue(this.patient.getPatronymic());
      phoneField.setValue(this.patient.getPhone());
    } else {
      nameField.setValue("");
      surnameField.setValue("");
      patronymicField.setValue("");
      phoneField.setValue("");
    }
    getOKButton().addClickListener(event -> {
      if (formIsValid()) {
        try {
          name = nameField.getValue();
          surname = surnameField.getValue();
          patronymic = patronymicField.getValue();
          phone = phoneField.getValue();
          if (this.patient == null) {
            this.patient = new Patient(name, surname, patronymic, phone);
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
          this.patient = null;
          isOpened = false;
          close();
        }
      } else {
        Notification.show("Данные введены не полностью или неверный формат данных", Notification.Type.TRAY_NOTIFICATION);
      }
    });
  }
  
  @Override
  protected Boolean formIsValid(){
    Boolean personsFieldsValid = super.formIsValid();
    phoneField.setValidationVisible(true);
//    try {
//      phoneField.validate();
//    } catch (Validator.InvalidValueException e) {
//      Notification.show("Необходимо правильно заполнить поле 'Телефон'", Notification.Type.ERROR_MESSAGE);
//    }
    return  personsFieldsValid && phoneField.isValid();
  }
}
