package com.haulmont.testtask.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class Helpers {
  static public void prepareComboBoxFilter(ComboBox comboBox, String inputPrompt, List<String> strings, Grid.HeaderCell filter) {
//    grid.deselectAll();
    comboBox.setImmediate(true);
    comboBox.setInputPrompt(inputPrompt);
    comboBox.setFilteringMode(FilteringMode.CONTAINS);
    comboBox.setInvalidAllowed(false);
    comboBox.setNullSelectionAllowed(true);
    comboBox.setWidth("100%");
    comboBox.addStyleName(ValoTheme.TEXTFIELD_TINY);
    comboBox.setContainerDataSource(new BeanItemContainer<>(String.class, strings));
//    comboBox.addValueChangeListener(event -> updateList(filterPrescriptionTextField.getValue()));
    filter.setComponent(comboBox);
  }
}
