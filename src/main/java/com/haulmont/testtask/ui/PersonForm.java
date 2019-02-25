package com.haulmont.testtask.ui;

import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import org.springframework.data.repository.CrudRepository;

public class PersonForm<T> extends ModalForm<T>{
  public PersonForm(String caption, UI hostUI, Long entityId, CrudRepository<T, Long> repository) {
    
    super(caption, hostUI, entityId, repository);
    
    final TextField surname = new TextField("Surname");
    final TextField name = new TextField("Name");
    final TextField patronymic = new TextField("Patronymic");
    
    form.addComponents(surname, name, patronymic);
    
  }
}
