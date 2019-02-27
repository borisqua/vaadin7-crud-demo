package com.haulmont.testtask.ui;

import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class PersonForm extends ModalDialog {
  
  protected final TextField surnameField = prepareTextField("Фамилия", "", true);
  protected final TextField nameField = prepareTextField("Имя", "", true);
  protected final TextField patronymicField = prepareTextField("Отчество", "", false);
  
  public PersonForm(String caption, UI hostUI) {
    
    super(caption, hostUI);
    
    form.addComponents(surnameField, nameField, patronymicField);
    
  }
  
  protected Boolean formIsValid() {
    nameField.setValidationVisible(true);
    surnameField.setValidationVisible(true);
    patronymicField.setValidationVisible(true);
//    try {
//      nameField.validate();
//      surnameField.validate();
//    } catch (Validator.InvalidValueException ignored) {
//      Notification.show("Поля имени и фамилии являются обязательными", Notification.Type.ERROR_MESSAGE);
//    }
//    patronymicField.validate();
    return nameField.isValid() && patronymicField.isValid() && surnameField.isValid();
  }
}
