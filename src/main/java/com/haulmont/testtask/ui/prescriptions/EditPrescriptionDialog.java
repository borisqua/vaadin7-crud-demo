package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.jpa.prescription.Prescription;
import com.haulmont.testtask.jpa.prescription.PrescriptionRepository;
import com.haulmont.testtask.jpa.prescription.Priority;
import com.haulmont.testtask.ui.ModalDialog;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  
  private static final Logger LOGGER = LoggerFactory.getLogger(EditPrescriptionDialog.class);
  
  EditPrescriptionDialog(String caption, UI hostUI, PrescriptionsGrid prescriptionsGrid, String titleString,
                         @Nullable Prescription prescription,
                         @Nullable PrescriptionRepository prescriptionRepository,
                         @Nullable DoctorRepository doctorRepository,
                         @Nullable PatientRepository patientRepository) {
    
    super(caption, hostUI, titleString);
    
    if (prescriptionRepository == null || doctorRepository == null || patientRepository == null) {
      return;
    }
    
    this.prescriptionsGrid = prescriptionsGrid;
    
    this.doctorComboBox = prepareStringCombo("Врач", "Ф.И.О.", 10, FilteringMode.CONTAINS,
      doctorRepository.getAllDoctorsFullNames(), null, true);
    this.patientComboBox = prepareStringCombo("Пациент", "Ф.И.О.", 10, FilteringMode.CONTAINS,
      patientRepository.getAllPatientsFullNameAndId(), null, true);
    this.prescriptionField = prepareTextArea("Рецепт", 10, 23, true);
    this.priorityComboBox = prepareStringCombo("Приоритет", "", 10, FilteringMode.CONTAINS,
      Stream.of(Priority.values()).map(Priority::toString).collect(Collectors.toList()), "Нормальный", true);
    this.issueDateField = prepareDateField("Дата выдачи", true);
    this.validityLengthField = prepareIntegerField("Срок действия", 7, true);
    
    this.prescription = prescription;
    this.prescriptionRepository = prescriptionRepository;
    
    form.addComponents(this.doctorComboBox, this.patientComboBox, this.prescriptionField,
      this.priorityComboBox, this.issueDateField, this.validityLengthField);
    
    if (this.prescription != null) {
      this.prescriptionField.setValue(this.prescription.getDescription());
      doctorRepository.findById(this.prescription.getDoctorId()).ifPresent(d ->
        this.doctorComboBox.setValue(d.getId() + ": " + ((d.getName().trim() + " " + d.getPatronymic()).trim() + " " + d.getSurname()).trim()));
      patientRepository.findById(this.prescription.getPatientId()).ifPresent(p ->
        this.patientComboBox.setValue(p.getId() + ": " + ((p.getName().trim() + " " + p.getPatronymic()).trim() + " " + p.getSurname()).trim()));
      this.priorityComboBox.setValue(this.prescription.getPriority());
      this.issueDateField.setValue(java.sql.Date.valueOf(this.prescription.getCreationDate()));
      this.validityLengthField.setValue(String.valueOf(this.prescription.getValidityLength()));
    } else {
      this.prescriptionField.setValue("");
      this.doctorComboBox.setValue(null);
      this.patientComboBox.setValue(null);
      this.priorityComboBox.setValue("Нормальный");
      this.issueDateField.setValue(java.sql.Date.valueOf(LocalDate.now()));
      this.validityLengthField.setValue("7");
    }
    
    getOKButton().addClickListener(event -> {
      if (formIsValid()) {
        try {
          prescriptionText = this.prescriptionField.getValue();
          doctorString = this.doctorComboBox.getValue().toString();
          doctorId = Long.parseLong(doctorString.substring(0, doctorString.indexOf(':')));
          patientString = this.patientComboBox.getValue().toString();
          patientId = Long.parseLong(patientString.substring(0, patientString.indexOf(':')));
          priority = this.priorityComboBox.getValue().toString();
          issueDate = this.issueDateField.getValue();
          validityLength = Integer.parseInt(this.validityLengthField.getValue());
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
          close();
        }
      } else {
        Notification.show("Данные введены не полностью или неверный формат данных", Notification.Type.TRAY_NOTIFICATION);
      }
    });
    
    addCloseListener(event -> {
      setValidationVisibility(false);
      this.prescriptionField.setValue("");
      this.doctorComboBox.setValue(null);
      this.patientComboBox.setValue(null);
      this.priorityComboBox.setValue("Нормальный");
      this.issueDateField.setValue(java.sql.Date.valueOf(LocalDate.now()));
      this.validityLengthField.setValue("7");
      this.prescription = null;
      this.isOpened = false;
    });
    
  }
  
  private void setValidationVisibility(Boolean isVisible) {
    this.prescriptionField.setValidationVisible(isVisible);
    this.doctorComboBox.setValidationVisible(isVisible);
    this.patientComboBox.setValidationVisible(isVisible);
    this.priorityComboBox.setValidationVisible(isVisible);
    this.issueDateField.setValidationVisible(isVisible);
    this.validityLengthField.setValidationVisible(isVisible);
  }
  
  private Boolean formIsValid() {
    setValidationVisibility(true);
    return this.prescriptionField.isValid() && this.doctorComboBox.isValid() && this.patientComboBox.isValid() &&
      this.priorityComboBox.isValid() && this.issueDateField.isValid() && this.validityLengthField.isValid();
  }
  
}
