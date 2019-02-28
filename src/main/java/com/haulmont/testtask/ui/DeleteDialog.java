package com.haulmont.testtask.ui;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

@SuppressWarnings("unused")
public class DeleteDialog<T> extends ModalDialog {
  
  private final T entity;
  private final CrudRepository<T, Long> repository;
  
  public DeleteDialog(String caption, UI hostUI,
                      String labelString,
                      @Nullable T entity,
                      @Nullable CrudRepository<T, Long> repository) {
    
    super(caption, hostUI, labelString);
  
    this.entity = entity;
    this.repository = repository;
    
    if(entity == null || repository == null){
      return;
    }
  
    getOKButton().addClickListener(event -> {
      try {
        this.repository.delete(this.entity);
      } catch (DataIntegrityViolationException e) {
        Notification.show("Запись не может быть удалена. Похоже на эту запись существуют сслыки в рецептах, сначала нужно удалить эти рецепты", Notification.Type.TRAY_NOTIFICATION);
      } finally {
        isOpened = false;
        close();
      }
    });
  }
}
