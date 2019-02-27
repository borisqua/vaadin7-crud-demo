package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.jpa.prescription.Prescription;
import com.haulmont.testtask.jpa.prescription.PrescriptionRepository;
import com.haulmont.testtask.jpa.prescription.Priority;
import com.haulmont.testtask.ui.ModalDialog;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"SameParameterValue"})
class EditPrescriptionDialog extends ModalDialog {
  
  private String prescriptionText;
  private Long doctorId;
  private Long patientId;
  private String doctorString;
  private String patientString;
  private String priority;
  private Date issueDate;
  private Integer validityLength;
  private Prescription prescription;
  private PrescriptionRepository prescriptionRepository;
  private PrescriptionsGrid prescriptionsGrid;
  
  private ComboBox doctorComboBox;
  private ComboBox patientComboBox;
  private TextArea prescriptionField;
  private ComboBox priorityComboBox;
  private DateField issueDateField;
  private TextField validityLengthField;
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  EditPrescriptionDialog(String caption, UI hostUI, PrescriptionsGrid prescriptionsGrid,
                         @Nullable Prescription prescription,
                         @Nullable PrescriptionRepository prescriptionRepository,
                         @Nullable DoctorRepository doctorRepository,
                         @Nullable PatientRepository patientRepository) {
    
    super(caption, hostUI);
    
    if (prescriptionRepository == null || doctorRepository == null || patientRepository == null) {
      return;
    }
    
    this.prescriptionsGrid = prescriptionsGrid;
    
    this.doctorComboBox = prepareStringCombo("Врач", "Ф.И.О.", 10, FilteringMode.CONTAINS,
      doctorRepository.getAllDoctorsFullNames(), null, true);
    this.patientComboBox = prepareStringCombo("Пациент", "Ф.И.О.", 10, FilteringMode.CONTAINS,
      patientRepository.getAllPatientsFullNameAndId(), null, true);
    this.prescriptionField = prepareTextArea("Рецепт", 10, 23, true);
    this.priorityComboBox = prepareStringCombo("Приоритет", "", 10, FilteringMode.OFF,
      Stream.of(Priority.values()).map(Priority::toString).collect(Collectors.toList()), "Нормальный", true);
    this.issueDateField = prepareDateField("Дата выдачи", true);
    this.validityLengthField = prepareIntegerField("Срок действия", 7, true);
    
    this.prescription = prescription;
    this.prescriptionRepository = prescriptionRepository;
    
    form.addComponents(doctorComboBox, patientComboBox, prescriptionField, priorityComboBox, issueDateField, validityLengthField);
    
    if (this.prescription != null) {
      prescriptionField.setValue(this.prescription.getDescription());
      doctorRepository.findById(this.prescription.getDoctorId()).ifPresent(d ->
        doctorComboBox.setValue(d.getId() + ": " + ((d.getName().trim() + " " + d.getPatronymic()).trim() + " " + d.getSurname()).trim()));
      patientRepository.findById(this.prescription.getPatientId()).ifPresent(p ->
        patientComboBox.setValue(p.getId() + ": " + ((p.getName().trim() + " " + p.getPatronymic()).trim() + " " + p.getSurname()).trim()));
      priorityComboBox.setValue(this.prescription.getPriority());
      issueDateField.setValue(java.sql.Date.valueOf(this.prescription.getCreationDate()));
      validityLengthField.setValue(String.valueOf(this.prescription.getValidityLength()));
    } else {
      prescriptionField.setValue("");
      doctorComboBox.setValue("");
      patientComboBox.setValue("");
      priorityComboBox.setValue("Нормальный");
      issueDateField.setValue(java.sql.Date.valueOf(LocalDate.now()));
      validityLengthField.setValue("7");
    }
    
    getOKButton().addClickListener(event -> {
      if (formIsValid()) {
        try {
          prescriptionText = prescriptionField.getValue();
          doctorString = doctorComboBox.getValue().toString();
          doctorId = Long.parseLong(doctorString.substring(0, doctorString.indexOf(':')));
          patientString = patientComboBox.getValue().toString();
          patientId = Long.parseLong(patientString.substring(0, patientString.indexOf(':')));
          priority = priorityComboBox.getValue().toString();
          issueDate = issueDateField.getValue();
          validityLength = Integer.parseInt(validityLengthField.getValue());
          if (this.prescription == null) { //create new
            this.prescription = new Prescription(prescriptionText, patientId, doctorId,
              new java.sql.Date(issueDate.getTime()).toLocalDate(), validityLength, priority);
            this.prescriptionRepository.save(this.prescription);
          } else { // update existent
            this.prescription.setDescription(prescriptionText);
            this.prescription.setPatientId(patientId);
            this.prescription.setDoctorId(doctorId);
            this.prescription.setPriority(priority);
            this.prescription.setCreationDate(new java.sql.Date(issueDate.getTime()).toLocalDate());//fuck
            this.prescription.setValidityLength(validityLength);
            this.prescriptionRepository.save(this.prescription);
          }
          this.prescriptionsGrid.updateList(this.prescriptionsGrid.getPrescriptionTextFilter());
        } catch (DataIntegrityViolationException dataIntegrityError) {
          LOGGER.debug("HaulmontLOG4J2: DATA INTEGRITY ERROR WHILE SAVING PRESCRIPTION ENTITY -> {}", dataIntegrityError);
          dataIntegrityError.printStackTrace();
        } catch (NumberFormatException e) {
          LOGGER.debug("HaulmontLOG4J2:  UNKNOWN ERROR WHILE SAVING PRESCRIPTION ENTITY -> {}", e);
          e.printStackTrace();
        } finally {
          this.prescription = null;
          isOpened = false;
          close();
        }
      } else {
        Notification.show("Данные введены не полностью или неверный формат данных", Notification.Type.TRAY_NOTIFICATION);
      }
    });
  }
  
  private Boolean formIsValid() {
    prescriptionField.setValidationVisible(false);
    doctorComboBox.setValidationVisible(false);
    patientComboBox.setValidationVisible(false);
    priorityComboBox.setValidationVisible(false);
    issueDateField.setValidationVisible(false);
    validityLengthField.setValidationVisible(false);
//    try {
//      patientComboBox.validate();
//      doctorComboBox.validate();
//    } catch (Validator.InvalidValueException ignored) {
//      Notification.show("Необходимо выбрать имя из списка", Notification.Type.ERROR_MESSAGE);
//    }
//    try {
//      prescriptionField.validate();
//    } catch (Validator.InvalidValueException ignored) {
//      Notification.show("Необходимо заполнить содержание рецепта", Notification.Type.ERROR_MESSAGE);
//    }
//    try {
//      priorityComboBox.validate();
//    } catch (Validator.InvalidValueException ignored) {
//      Notification.show("Необходимо выбрать приоритет из списка", Notification.Type.ERROR_MESSAGE);
//    }
//    try {
//      issueDateField.validate();
//    } catch (Validator.InvalidValueException ignored) {
//      Notification.show("Необходимо правильно ввести дату выдачи рецепта", Notification.Type.ERROR_MESSAGE);
//    }
//    try {
//      validityLengthField.validate();
//    } catch (Validator.InvalidValueException ignored) {
//      Notification.show("Необходимо правильно срок действия рецепта в днях от 1 до 365", Notification.Type.ERROR_MESSAGE);
//    }
  
    return prescriptionField.isValid() && doctorComboBox.isValid() && patientComboBox.isValid() &&
      priorityComboBox.isValid() && issueDateField.isValid() && validityLengthField.isValid();
  }
  
}
