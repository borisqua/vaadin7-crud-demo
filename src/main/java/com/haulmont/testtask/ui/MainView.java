package com.haulmont.testtask.ui;

import com.haulmont.testtask.patient.PatientRepository;
import com.haulmont.testtask.prescription.Priority;
import com.haulmont.testtask.prescription.view.ViewAll;
import com.haulmont.testtask.prescription.view.ViewAllRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "SameParameterValue"})
@SpringUI
@Title("Haulmont test app")
@Theme("valo")
public class MainView extends UI {
  
  private final PatientRepository patientRepository;
  private final ViewAllRepository allPrescriptions;
  
  private Grid grid = new Grid("Рецепты");
  
  private final ComboBox filterPatientComboBox = new ComboBox(/*"Patient..."*/);
  private final ComboBox filterPriorityComboBox = new ComboBox(/*"Priority..."*/);
  private final TextField filterPrescriptionTextField = new TextField(/*"Pattern to search in prescriptions texts..."*/);
  private final Button addButton = new Button("Add");
  private final Button editButton = new Button("Edit");
  private final Button deleteButton = new Button("Delete");
  private ViewAll selectedPrescription;
  
  @Autowired
  public MainView(PatientRepository patientRepository, ViewAllRepository allPrescriptions) {
    this.patientRepository = patientRepository;
    this.allPrescriptions = allPrescriptions;
  }
  
  private void prepareComboBoxFilter(ComboBox comboBox, String inputPrompt, List<String> strings, Grid.HeaderCell filter) {
    comboBox.setImmediate(true);
    comboBox.setInputPrompt(inputPrompt);
    comboBox.setFilteringMode(FilteringMode.CONTAINS);
    comboBox.setInvalidAllowed(false);
    comboBox.setNullSelectionAllowed(true);
    comboBox.setWidth("100%");
    comboBox.addStyleName(ValoTheme.TEXTFIELD_TINY);
    comboBox.setContainerDataSource(new BeanItemContainer<>(String.class, strings));
    comboBox.addValueChangeListener(event -> updateList(filterPrescriptionTextField.getValue()));
    filter.setComponent(comboBox);
  }
  
  private void prepareTextFieldFilter(TextField textField, String inputPrompt, Grid.HeaderCell filter) {
    textField.setImmediate(true);
    textField.setInputPrompt(inputPrompt);
    textField.setWidth("100%");
    textField.addStyleName(ValoTheme.TEXTFIELD_TINY);
    textField.addTextChangeListener(event -> updateList(event.getText()));
    filter.setComponent(filterPrescriptionTextField);
  }
  
  protected void init(VaadinRequest vaadinRequest) {
  
    grid.setSizeFull();
    grid.setColumns(/*"id", */"doctor", "patient", "prescription", "priority", "expiration");
    
    //todo?? if there are selectionMode switched on, then error after updating any of filters
    // ( .. during execution of select statement in updateList() when there is selected row in the grid)
    grid.setSelectionMode(Grid.SelectionMode.NONE);
    grid.addSelectionListener(event -> {
        selectedPrescription = (ViewAll) event.getSelected().toArray()[0];
        Notification.show(selectedPrescription.toString());
      }
    );
  
    final VerticalLayout layout = new VerticalLayout();
    final Grid.HeaderRow filterRow = grid.appendHeaderRow();
    final Grid.HeaderCell patientFilter = filterRow.getCell("patient");
    final Grid.HeaderCell priorityFilter = filterRow.getCell("priority");
    final Grid.HeaderCell prescriptionFilter = filterRow.getCell("prescription");
    
    //live filter on patient name
    prepareComboBoxFilter(filterPatientComboBox, "Пациент...", patientRepository.getAllPatientsFullNames(), patientFilter);
    //live filter on priority name
    prepareComboBoxFilter(filterPriorityComboBox, "Приоритет...", Stream.of(Priority.values()).map(Priority::toString).collect(Collectors.toList()), priorityFilter);
    //live filter on prescription text
    prepareTextFieldFilter(filterPrescriptionTextField, "Фрагмент рецепта...", prescriptionFilter);
    
    layout.addComponents(grid);
    layout.setExpandRatio(grid, 2);
    
    updateList(filterPrescriptionTextField.getValue());
    
    layout.setMargin(true);
    setContent(layout);
  }
  
  private void updateList(String prescriptionText) {
//    if (String.valueOf(filterPatientComboBox.getValue()).trim().length() > 0) {
    grid.setContainerDataSource(
      new BeanItemContainer<>(ViewAll.class, allPrescriptions.findByCustomCriteria(
        (String) filterPatientComboBox.getValue(),   //patient name pattern
        (String) filterPriorityComboBox.getValue(),  //priority name pattern
        prescriptionText)
      ));
    Notification.show("Применён фильтр:");
//    } else {
//      grid.setContainerDataSource(
//        new BeanItemContainer<>(ViewAll.class, (List<ViewAll>) allPrescriptions.findAll()));
//      Notification.show("Полный список:");
//    }
  }
  
  @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
  @VaadinServletConfiguration(ui = MainView.class, productionMode = false)
  public static class MyUIServlet extends VaadinServlet {
  }
}
