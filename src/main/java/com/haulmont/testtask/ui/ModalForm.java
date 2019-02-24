package com.haulmont.testtask.ui;

import com.vaadin.ui.*;
import org.springframework.data.repository.CrudRepository;

//@SpringView
//@SuppressWarnings("unused")
public class ModalForm<T> extends Window {
  
  protected FormLayout form;
  private boolean isOpened = false;
  private UI hostUI;
  
  public ModalForm(String caption, UI hostUI, T entity , CrudRepository<T, Long> repository) {
    super(caption);
  
    center();
    setModal(true);
    
    this.hostUI = hostUI;
  
    VerticalLayout layout = new VerticalLayout();
    layout.setSizeFull();
    layout.setMargin(true);
    layout.setSpacing(true);
  
    this.form = new FormLayout();
    form.setSizeFull();
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
    buttonCancel.addClickListener(e -> close());
    buttonOK.addClickListener(e -> {
      repository.save(entity);
      close();
    });
  
    layout.addComponents(form, buttonsLayout);
    layout.setComponentAlignment(form, Alignment.TOP_CENTER);
    layout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_CENTER);
    
    addCloseListener(e -> this.isOpened = false);
  
    setContent(layout);
    layout.setSizeUndefined();
    
  }
  
  public void open(){
    if(!this.isOpened){
      hostUI.addWindow(this);
    }else{
      hostUI.focus();
    }
    this.isOpened = true;
  }
  
  public boolean isOpened() {
    return isOpened;
  }
}
