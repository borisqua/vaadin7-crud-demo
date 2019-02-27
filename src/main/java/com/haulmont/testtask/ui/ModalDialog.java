package com.haulmont.testtask.ui;

import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings({"SameParameterValue", "unused"})
public class ModalDialog extends Window {
  
  protected FormLayout form;
  
  protected boolean isOpened = false;
  private UI hostUI;
  
  private Button buttonOK = new Button("OK");
  private Button buttonCancel = new Button("Cancel");
  
  public ModalDialog(String caption, UI hostUI) {
    
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
                                        FilteringMode filteringMode, @Nullable List<String> container, @Nullable String defaultString, Boolean required) {
    
    ComboBox combo = new ComboBox(caption, container);
    combo.setNullSelectionItemId(nullCaption);
    combo.setWidth(widthCm, Unit.CM);
    combo.setRequired(required);
    combo.setRequiredError("Выберите значение из списка");
    combo.setNullSelectionAllowed(false);
    combo.setFilteringMode(filteringMode);
    combo.setValue(defaultString);
    combo.setImmediate(true);
    combo.setValidationVisible(false);
    
    return combo;
    
  }
  
  protected TextArea prepareTextArea(String caption, Integer rows, Integer columns, Boolean required) {
    
    TextArea textArea = new TextArea(caption);
    textArea.setRows(rows);
    textArea.setColumns(columns);
    textArea.setRequired(required);
    textArea.setRequiredError("Необходимо заполнить поле");
    textArea.setImmediate(true);
    textArea.setValidationVisible(false);
    
    return textArea;
  }
  
  protected DateField prepareDateField(String caption, Boolean required) {
    
    DateField dateField = new DateField(caption, java.sql.Date.valueOf(LocalDate.now()));
    dateField.setRequired(required);
    dateField.setRequiredError("Необходимо заполнить поле");
    dateField.setImmediate(true);
    dateField.setValidationVisible(false);
    
    return dateField;
  }
  
  protected TextField prepareIntegerField(String caption, Integer defaultValue, Boolean required) {
    
    TextField textField = new TextField(caption, String.valueOf(defaultValue));
    textField.setRequired(required);
    textField.setRequiredError("Необходимо заполнить поле");
    textField.setConverter(new StringToIntegerConverter());
    textField.addValidator(new IntegerRangeValidator("Необходимо указать срок действия рецепта в днях, возможные значения - число от 1 до 365", 1, 365));
    textField.setImmediate(true);
    textField.setValidationVisible(false);
    
    return textField;
  }
  
  protected TextField prepareTextField(String caption, String defaultValue, Boolean required) {
    
    TextField textField = new TextField(caption, defaultValue);
    textField.setRequired(required);
    textField.setRequiredError("Необходимо заполнить поле");
    textField.setImmediate(true);
    textField.setValidationVisible(false);
    
    return textField;
  }
  
  protected TextField preparePhoneField(String caption, Boolean required) {
    
    TextField textField = new TextField(caption, "");
    textField.setRequired(required);
    textField.setRequiredError("Необходимо заполнить поле");
    textField.addValidator(new RegexpValidator("(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?", true, "Неверный формат телефонного номера"));
    textField.setImmediate(true);
    textField.setValidationVisible(false);
    
    return textField;
  }
}
