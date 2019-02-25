package com.haulmont.testtask.ui;

import com.vaadin.ui.*;
import org.springframework.data.repository.CrudRepository;

//@SpringView
//@SuppressWarnings("unused")
public class ModalForm<T> extends ModalDialog {
  
  private T entity;
  
  public ModalForm(String caption, UI hostUI, Long entityId , CrudRepository<T, Long> repository) {
    
    super(caption, hostUI);
  
    repository.findById(entityId).ifPresent(e->entity = e);
    
    buttonOK.addClickListener(e -> {
      repository.save(entity);
    });
  }
}
