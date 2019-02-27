package com.haulmont.testtask.ui;

import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings({"SameParameterValue", "unused"})
public class ModalDialog extends Window {
  
  protected FormLayout form;
  
  private boolean isOpened = false;
  private UI hostUI;
  
  private Button buttonOK = new Button("OK");
  private Button buttonCancel = new Button("Cancel");
  
  public ModalDialog(String caption, UI hostUI) {
    
    super(caption);
  
    buttonOK.setEnabled(false);
    
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
    
    buttonOK.addClickListener(event -> {
      this.isOpened = false;
      close();
    });
    
    buttonCancel.addClickListener(event -> {
      this.isOpened = false;
      close();
    });
    
  }
  
  protected Button getOKButton() {
    return buttonOK;
  }
  
  protected Button getCancelButton() {
    return buttonCancel;
  }
  
  public void open() {
    if (!this.isOpened) {
      hostUI.addWindow(this);
    } else {
      hostUI.focus();
    }
    this.isOpened = true;
  }
  
  protected ComboBox prepareStringCombo(String caption, String nullCaption, Integer widthCm,
                                        FilteringMode filteringMode, @Nullable List<String> container, @Nullable String defaultString) {
    
    ComboBox combo = new ComboBox(caption, container);
    combo.setNullSelectionItemId(nullCaption);
    combo.setWidth(widthCm, Unit.CM);
    combo.setImmediate(false);
    combo.setRequired(true);
    combo.setNullSelectionAllowed(false);
    combo.setFilteringMode(filteringMode);
    combo.setValue(defaultString);
    
    return combo;
    
  }
  
  protected TextArea prepareTextArea(String caption, Integer rows, Integer columns) {
    TextArea textArea = new TextArea(caption);
    textArea.setRows(rows);
    textArea.setColumns(columns);
    textArea.setImmediate(true);
    textArea.setRequired(true);
    
    return textArea;
  }
  
  protected DateField prepareDateField(String caption) {
    
    DateField dateField = new DateField(caption, java.sql.Date.valueOf(LocalDate.now()));
    dateField.setRequired(true);
    dateField.setImmediate(true);
    
    return dateField;
  }
  
}
