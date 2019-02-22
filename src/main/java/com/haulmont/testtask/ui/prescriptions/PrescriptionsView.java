package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.jpa.prescription.Priority;
import com.haulmont.testtask.jpa.prescription.view.ViewAll;
import com.haulmont.testtask.jpa.prescription.view.ViewAllRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "SameParameterValue", "FieldCanBeLocal"})
@SpringView
@Title("Haulmont test app / Prescriptions")
@Theme("valo")
public class PrescriptionsView extends VerticalLayout implements View {
  
  private final PatientRepository patientRepository;
  private final ViewAllRepository allPrescriptions;
  
  private Grid grid = new Grid();
  
  private final ComboBox filterPatientComboBox = new ComboBox();
  private final ComboBox filterPriorityComboBox = new ComboBox();
  
  private final TextField filterPrescriptionTextField = new TextField(/*"Pattern to search in prescriptions texts..."*/);
  
  private final Button addButton = new Button("Add");
  private final Button editButton = new Button("Edit");
  private final Button deleteButton = new Button("Delete");
  
  private ViewAll viewPrescription;
  
  public PrescriptionsView(PatientRepository patientRepository, ViewAllRepository allPrescriptions) {
    this.patientRepository = patientRepository;
    this.allPrescriptions = allPrescriptions;
    
    Label label = new Label("Рецепты");
    label.setStyleName(ValoTheme.LABEL_HUGE);
    
    grid.setSizeFull();
    grid.setColumns(/*"id", */"doctor", "patient", "prescription", "priority", "expiration");
    grid.setEditorEnabled(false);
    
    grid.setSelectionMode(Grid.SelectionMode.SINGLE);
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          viewPrescription = (ViewAll) event.getSelected().toArray()[0];
          Notification.show(viewPrescription.toString(), Notification.Type.TRAY_NOTIFICATION);
        }
      }
    );
    
    
    Grid.HeaderRow filterRow = grid.appendHeaderRow();
    Grid.HeaderCell patientFilter = filterRow.getCell("patient");
    Grid.HeaderCell priorityFilter = filterRow.getCell("priority");
    Grid.HeaderCell prescriptionFilter = filterRow.getCell("prescription");
    Grid.Column doctorColumn = grid.getColumn("doctor").setHeaderCaption("Врач");
    Grid.Column patientColumn = grid.getColumn("patient").setHeaderCaption("Пациент");
    Grid.Column prescriptionColumn = grid.getColumn("prescription").setHeaderCaption("Рецепт");
    Grid.Column priorityColumn = grid.getColumn("priority").setHeaderCaption("Приоритет");
    Grid.Column expirationColumn = grid.getColumn("expiration").setHeaderCaption("Срок действия");
    
    //live filter on patient name
    prepareComboBoxFilter(filterPatientComboBox, "Пациент...", patientRepository.getAllPatientsFullNames(), patientFilter);
    //live filter on priority name
    prepareComboBoxFilter(filterPriorityComboBox, "Приоритет...", Stream.of(Priority.values()).map(Priority::toString).collect(Collectors.toList()), priorityFilter);
    //live filter on prescription text
    prepareTextFieldFilter(filterPrescriptionTextField, "Рецепт...", prescriptionFilter);
    
    setWidth(100, Sizeable.Unit.PERCENTAGE);
    
    updateList(filterPrescriptionTextField.getValue());
    
    setMargin(true);
    addComponents(label, grid);
    setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
  }
  
  private void prepareComboBoxFilter(ComboBox comboBox, String inputPrompt, List<String> strings, Grid.HeaderCell filter) {
    grid.deselectAll();
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
    grid.deselectAll();
    textField.setImmediate(true);
    textField.setInputPrompt(inputPrompt);
    textField.setWidth("100%");
    textField.addStyleName(ValoTheme.TEXTFIELD_TINY);
    textField.addTextChangeListener(event -> updateList(event.getText()));
    filter.setComponent(filterPrescriptionTextField);
  }
  
  @Override
  public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    
    Notification.show("Prescriptions", Notification.Type.HUMANIZED_MESSAGE);
    updateList(filterPrescriptionTextField.getValue());

//    setExpandRatio(grid, 1);//todo?? what is it
  
  }
  
  private void updateList(String prescriptionText) {
    grid.deselectAll();
    final BeanItemContainer<ViewAll> container = new BeanItemContainer<>(ViewAll.class, allPrescriptions.findByCustomCriteria(
      (String) filterPatientComboBox.getValue(),   //patient name pattern
      (String) filterPriorityComboBox.getValue(),  //priority name pattern
      prescriptionText)
    );
    final GeneratedPropertyContainer wrapContainer = new GeneratedPropertyContainer(container);//todo>> to add rendered content
    grid.setContainerDataSource(wrapContainer);
//    Notification.show("Данные обновлены.", Notification.Type.TRAY_NOTIFICATION);
  }
  
}
