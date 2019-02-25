package com.haulmont.testtask.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class Helpers {
  
  static public void prepareComboBox(ComboBox comboBox, String inputPrompt, List<String> strings) {
    comboBox.setImmediate(true);
    comboBox.setInputPrompt(inputPrompt);
    comboBox.setFilteringMode(FilteringMode.CONTAINS);
    comboBox.setInvalidAllowed(false);
    comboBox.setNullSelectionAllowed(true);
    comboBox.setWidth("100%");
    comboBox.addStyleName(ValoTheme.TEXTFIELD_TINY);
    comboBox.setContainerDataSource(new BeanItemContainer<>(String.class, strings));
  }
  
  static public void prepareTextField(TextField textField, String inputPrompt){
    textField.setImmediate(true);
    textField.setInputPrompt(inputPrompt);
    textField.setWidth("100%");
    textField.addStyleName(ValoTheme.TEXTFIELD_TINY);
  }
  
}
