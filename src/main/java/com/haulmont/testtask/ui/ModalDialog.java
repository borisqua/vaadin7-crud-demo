package com.haulmont.testtask.ui;

import com.vaadin.ui.*;

public class ModalDialog extends Window {
  
  protected FormLayout form;
  protected boolean isOpened = false;
  private UI hostUI;
  
  protected Button buttonOK = new Button("OK");
  protected Button buttonCancel = new Button("Cancel");
  
  public ModalDialog(String caption, UI hostUI) {
    
    super(caption);
    
    center();
    setModal(true);
    
    this.hostUI = hostUI;
    
    VerticalLayout layout = new VerticalLayout();
    layout.setSizeFull();
    layout.setMargin(true);
    layout.setSpacing(true);
//    setWidth("50%");
    
    this.form = new FormLayout();
    form.setSizeFull();
    form.setWidth("100%");
    form.setMargin(true);
    form.setSpacing(true);
    
    HorizontalLayout buttonsLayout = new HorizontalLayout();
    Button buttonOK = new Button("OK");
    Button buttonCancel = new Button("Cancel");
    buttonsLayout.addComponents(buttonOK, buttonCancel);
    buttonsLayout.setMargin(true);
    buttonsLayout.setSpacing(true);
    buttonsLayout.setSizeFull();
    buttonsLayout.setComponentAlignment(buttonOK, Alignment.MIDDLE_CENTER);
    buttonsLayout.setComponentAlignment(buttonCancel, Alignment.MIDDLE_CENTER);
    
    layout.addComponents(form, buttonsLayout);
    layout.setComponentAlignment(form, Alignment.TOP_CENTER);
    layout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_CENTER);
    
    addCloseListener(e -> this.isOpened = false);
    
    setContent(layout);
    layout.setSizeUndefined();
  
//    buttonOK.addClickListener(event->{
//      this.isOpened = false;
//    });
  
//    buttonCancel.addClickListener(event->{
//      this.isOpened = false;
//    });
    
  }
  
  public void open() {
    if (!this.isOpened) {
      hostUI.addWindow(this);
    } else {
      hostUI.focus();
    }
    this.isOpened = true;
  }
  
}
