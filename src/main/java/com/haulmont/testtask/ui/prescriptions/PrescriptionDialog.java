package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.jpa.prescription.Prescription;
import com.haulmont.testtask.jpa.prescription.PrescriptionRepository;
import com.haulmont.testtask.jpa.prescription.Priority;
import com.haulmont.testtask.ui.ModalDialog;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"SameParameterValue"})
class PrescriptionDialog extends ModalDialog {
  
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
  
  private ComboBox doctorComboBox;
  private ComboBox patientComboBox;
  private TextArea prescriptionField;
  private ComboBox priorityComboBox;
  private DateField issueDateField;
  private TextField validityLengthField;
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  PrescriptionDialog(String caption, UI hostUI,
                     @Nullable Prescription prescription,
                     @Nullable PrescriptionRepository prescriptionRepository,
                     @Nullable DoctorRepository doctorRepository,
                     @Nullable PatientRepository patientRepository) {
    
    super(caption, hostUI);
    
    if(prescriptionRepository == null || doctorRepository == null || patientRepository == null){
      return;
    }
    
    this.doctorComboBox = prepareStringCombo("Врач", "Ф.И.О.", 10, FilteringMode.CONTAINS,
      doctorRepository.getAllDoctorsFullNames(), null);
    this.patientComboBox = prepareStringCombo("Врач", "Ф.И.О.", 10, FilteringMode.CONTAINS,
      patientRepository.getAllPatientsFullNameAndId(), null);
    this.prescriptionField = prepareTextArea("рецепт", 10, 23);
    this.priorityComboBox = prepareStringCombo("Приоритет", "", 10, FilteringMode.OFF,
      Stream.of(Priority.values()).map(Priority::toString).collect(Collectors.toList()), "Нормальный");
    this.issueDateField = prepareDateField("дата выдачи");
    this.validityLengthField = new TextField("Срок действия", String.valueOf(7));
    this.prescription = prescription;
    this.prescriptionRepository = prescriptionRepository;
    validityLengthField.addValidator(new IntegerRangeValidator("Срок действия рецепта - от одного до 365 дней", 1, 365));
    validityLengthField.setImmediate(true);
    
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
    }
    
    getOKButton().addClickListener(event -> {
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
          this.prescription = new Prescription(prescriptionText, patientId, doctorId, new java.sql.Date(issueDate.getTime()).toLocalDate(), validityLength, priority);
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
      } catch (DataIntegrityViolationException dataIntegrityError) {
        LOGGER.debug("HaulmontLOG4J2: DATA INTEGRITY ERROR WHILE SAVING PRESCRIPTIONS ENTITY -> {}", dataIntegrityError);
        dataIntegrityError.printStackTrace();
      } catch (NumberFormatException e) {
        LOGGER.debug("HaulmontLOG4J2:  UNKNOWN ERROR WHILE SAVING PRESCRIPTIONS ENTITY -> {}", e);
        e.printStackTrace();
      }
    });
  }
  
  
}
