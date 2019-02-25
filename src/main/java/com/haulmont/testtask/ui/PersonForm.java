package com.haulmont.testtask.ui;

import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class PersonForm extends ModalDialog{
  
  protected final TextField surname = new TextField("Фамилия");
  protected final TextField name = new TextField("Имя");
  protected final TextField patronymic = new TextField("Отчество");
  
  public PersonForm(String caption, UI hostUI) {
    
    super(caption, hostUI);
    
    form.addComponents(surname, name, patronymic);
    
  }
}
