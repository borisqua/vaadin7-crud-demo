package com.haulmont.testtask.ui;

import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;

@SuppressWarnings("unused")
public class DeleteDialog<T> extends ModalDialog {
  
  public DeleteDialog(String caption, UI hostUI, String labelString, T entity, CrudRepository<T, Long> repository) {
    
    super(caption, hostUI);
    
    Label label = new Label(labelString);
    label.setStyleName(ValoTheme.LABEL_HUGE);
    form.addComponent(label);
    
    buttonOK.addClickListener(event -> {
      try {
        repository.delete(entity);
      } catch (DataIntegrityViolationException e) {
        Notification.show("Запись не может быть удалена. Похоже на эту запись существуют сслыки в рецептах, сначала нужно удалить эти рецепты", Notification.Type.ERROR_MESSAGE);
      }
      close();
    });
    buttonCancel.addClickListener(event->close());
  }
}
