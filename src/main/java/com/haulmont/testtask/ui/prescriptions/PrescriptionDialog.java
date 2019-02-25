package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.doctor.Doctor;
import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.jpa.patient.Patient;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.jpa.prescription.Prescription;
import com.haulmont.testtask.jpa.prescription.PrescriptionRepository;
import com.haulmont.testtask.jpa.prescription.Priority;
import com.haulmont.testtask.ui.ModalDialog;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PrescriptionDialog extends ModalDialog {
  
  private String prescriptionText;
  private Long doctorId;
  private Long patientId;
  private String priority;
  private Date issueDate;
  private Integer validityLength;
  
  private final ComboBox doctorComboBox = new ComboBox("Врач");
  private final ComboBox patientComboBox = new ComboBox("Пациент");
  private final TextArea prescriptionField = new TextArea("Рецепт");
  private final ComboBox priorityComboBox = new ComboBox("Приоритет");
  private final DateField issueDateField = new DateField("Дата выдачи", new Date());
  private final TextField validityLengthField = new TextField("Срок действия", String.valueOf(7));
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  PrescriptionDialog(String caption, UI hostUI,
                     @Nullable Prescription prescription,
                     PrescriptionRepository prescriptionRepository,
                     DoctorRepository doctorRepository,
                     PatientRepository patientRepository) {
    
    super(caption, hostUI);
    
    doctorComboBox.setSizeFull();
    doctorComboBox.setWidth("100%");
    doctorComboBox.setImmediate(true);
    doctorComboBox.setRequired(true);
    doctorComboBox.setNullSelectionAllowed(false);
    doctorComboBox.setContainerDataSource(new BeanItemContainer<>(Doctor.class, (List<Doctor>) doctorRepository.findAll()));

//    doctorComboBox.addValidator(new StringLengthValidator("Имя должно быть строкой длиной как минимум 2 симмвола", 2, 20, false));
//    doctorComboBox.setIcon(FontAwesome.USER);
    
    patientComboBox.setSizeFull();
    patientComboBox.setWidth("100%");
    patientComboBox.setImmediate(true);
    patientComboBox.setRequired(true);
    patientComboBox.setNullSelectionAllowed(false);
    patientComboBox.setContainerDataSource(new BeanItemContainer<>(Patient.class, (List<Patient>) patientRepository.findAll()));
//    patientComboBox.addValidator(new NullValidator("", false));
//    patientComboBox.setIcon(FontAwesome.USER);
    
    prescriptionField.setRows(10);
    prescriptionField.setImmediate(true);
    prescriptionField.setSizeFull();
    prescriptionField.setWidth("100%");
    patientComboBox.setRequired(true);
//    prescriptionField.setIcon(FontAwesome.FILE_TEXT_O);
    
    List<String> strings = Stream.of(Priority.values()).map(Priority::toString).collect(Collectors.toList());
    priorityComboBox.setSizeFull();
    priorityComboBox.setImmediate(true);
    priorityComboBox.setContainerDataSource(new BeanItemContainer<>(String.class, strings));
    priorityComboBox.setRequired(true);
    priorityComboBox.setNullSelectionAllowed(false);
    priorityComboBox.setValue("Нормальный");
    
    issueDateField.setRequired(true);
    issueDateField.setImmediate(true);
    
    validityLengthField.addValidator(new IntegerRangeValidator("Срок действия рецепта - от одного до 365 дней", 1, 365));
    validityLengthField.setImmediate(true);
    
    form.addComponents(doctorComboBox, patientComboBox, prescriptionField, priorityComboBox, issueDateField, validityLengthField);
    
    if(prescription !=null){
      prescriptionField.setValue(prescription.getDescription());
      doctorComboBox.setValue(doctorRepository.findById(prescription.getDoctorId()));
      patientComboBox.setValue(patientRepository.findById(prescription.getPatientId()));
      priorityComboBox.setValue(prescription.getPriority());
      issueDateField.setValue(prescription.getCreationDate());
      validityLengthField.setValue(String.valueOf(prescription.getValidityLength()));
    }
    buttonOK.addClickListener(event -> {
      if (prescription == null) { //create new
        prescriptionText = prescriptionField.getValue();
        doctorId = Long.valueOf(doctorComboBox.getId());
        patientId = Long.valueOf(patientComboBox.getId());
        priority = (String) priorityComboBox.getValue();
        issueDate = issueDateField.getValue();
        validityLength = Integer.valueOf(validityLengthField.getValue());
        prescriptionRepository.save(new Prescription(prescriptionText, patientId, doctorId, issueDate, validityLength, priority));
//  public Prescription(String description, Long patientId, Long doctorId,  Date creationDate, Integer validityLengthField, String priority) {
      } else { //update
        prescription.setDescription(prescriptionField.getValue());
        prescription.setPatientId(Long.valueOf(patientComboBox.getId()));
        prescription.setDoctorId(Long.valueOf(doctorComboBox.getId()));
        prescription.setPriority((String) priorityComboBox.getValue());
        prescription.setCreationDate((java.sql.Date) issueDateField.getValue());
        prescription.setValidityLength(Integer.valueOf(validityLengthField.getValue()));
        prescriptionRepository.save(prescription);
      }
      isOpened = false;
      close();
    });
    
    buttonCancel.addClickListener(event -> {
      isOpened = false;
      close();
    });
  }
  
}
