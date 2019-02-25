package com.haulmont.testtask.ui;

import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

public class DeleteDialog<T> extends ModalDialog{
  
  private Label label;
  private T entity;
  
  public DeleteDialog(String caption, UI hostUI, String labelString) {
    super(caption, hostUI);
    this.label = new Label(labelString);
    this.label.setStyleName(ValoTheme.LABEL_HUGE);
    form.addComponent(label);
    
  }
}
