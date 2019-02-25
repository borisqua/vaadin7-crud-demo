package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.prescription.Prescription;
import com.haulmont.testtask.jpa.prescription.PrescriptionRepository;
import com.haulmont.testtask.ui.ModalForm;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.springframework.lang.Nullable;

import java.util.Date;

class PrescriptionDialog extends ModalForm<Prescription> {
  
  PrescriptionDialog(String caption, UI hostUI, Long prescriptionId, PrescriptionRepository prescriptionRepository) {
  
    super(caption, hostUI, prescriptionId, prescriptionRepository);
  
    final ComboBox doctorComboBox = new ComboBox("Врач");
    doctorComboBox.setSizeFull();
    doctorComboBox.setIcon(FontAwesome.USER);
    doctorComboBox.setRequired(true);
    doctorComboBox.addValidator(new NullValidator("asdf", false));
    
    final ComboBox patientComboBox = new ComboBox("Пациент");
    patientComboBox.setSizeFull();
    
    final TextArea prescription = new TextArea("Рецепт");
    prescription.setRows(10);
    prescription.setSizeFull();
    prescription.setWidth("50%");
    
    final ComboBox priorityComboBox = new ComboBox("Приоритет");
    priorityComboBox.setSizeFull();
    
    final DateField issueDate = new DateField("Дата выдачи", new Date());
    
    final TextField validityLength = new TextField("Срок действия");
    validityLength.addValidator(new IntegerRangeValidator("asdf", 1, 365));
    
    form.addComponents(doctorComboBox, patientComboBox, prescription, priorityComboBox, issueDate, validityLength);
  }
  
  void open(@Nullable Prescription prescription){
    super.open();
    if(prescription != null){
      //fill up form with prescription date
    } else {
//      prescription = new Prescription()
    }
  }
}
