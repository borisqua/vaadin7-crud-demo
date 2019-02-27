package com.haulmont.testtask.ui;

import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class PersonForm extends ModalDialog{
  
  protected final TextField surnameField = new TextField("Фамилия");
  protected final TextField nameField = new TextField("Имя");
  protected final TextField patronymicField = new TextField("Отчество");
  
  public PersonForm(String caption, UI hostUI) {
    
    super(caption, hostUI);
    
    form.addComponents(surnameField, nameField, patronymicField);
    
  }
}
