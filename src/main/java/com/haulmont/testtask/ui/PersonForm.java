package com.haulmont.testtask.ui;

import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class PersonForm extends ModalDialog {
  
  protected final TextField surnameField;
  protected final TextField nameField;
  protected final TextField patronymicField;
  
  public PersonForm(String caption, UI hostUI, String titleString) {
    
    super(caption, hostUI, titleString);
  
    this.surnameField = prepareTextField("Фамилия", "", true);
    this.nameField = prepareTextField("Имя", "", true);
    this.patronymicField = prepareTextField("Отчество", "", false);
    
    form.addComponents(this.surnameField, this.nameField, this.patronymicField);
    
    addCloseListener(event->{
      setValidationVisibility(false);
      surnameField.setValue("");
      nameField.setValue("");
      patronymicField.setValue("");
    });
    
  }
  
  protected void setValidationVisibility(Boolean isVisible) {
    this.nameField.setValidationVisible(isVisible);
    this.surnameField.setValidationVisible(isVisible);
    this.patronymicField.setValidationVisible(isVisible);
  }
  
  protected Boolean formIsValid() {
    setValidationVisibility(true);
//    try {
//      nameField.validate();
//      surnameField.validate();
//    } catch (Validator.InvalidValueException ignored) {
//      Notification.show("Поля имени и фамилии являются обязательными", Notification.Type.TRAY_NOTIFICATION);
//    }
//    patronymicField.validate();
    return this.nameField.isValid() && this.patronymicField.isValid() && this.surnameField.isValid();
  }
}
