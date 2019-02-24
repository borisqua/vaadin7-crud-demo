package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.jpa.prescription.PrescriptionRepository;
import com.haulmont.testtask.jpa.prescription.Priority;
import com.haulmont.testtask.jpa.prescription.view.PrescriptionHumanized;
import com.haulmont.testtask.jpa.prescription.view.PrescriptionHumanizedRepository;
import com.haulmont.testtask.ui.GridForm;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"unused"})
@SpringView
@Title("Haulmont test app / Prescriptions")
@Theme("valo")
public class PrescriptionsGrid extends GridForm<PrescriptionHumanized> {
  
  private Long prescriptionId = -1L;
  
  private PrescriptionHumanizedRepository prescriptionHumanizedRepository;
  
  private final ComboBox filterPatientComboBox = new ComboBox();
  private final ComboBox filterPriorityComboBox = new ComboBox();
  private final TextField filterPrescriptionTextField = new TextField();
  
  public PrescriptionsGrid(PatientRepository patientRepository,
                           PrescriptionRepository prescriptionRepository,
                           PrescriptionHumanizedRepository prescriptionHumanizedRepository) {
    
    super(PrescriptionHumanized.class, "Рецепты", prescriptionHumanizedRepository);
    
    this.prescriptionHumanizedRepository = prescriptionHumanizedRepository;
    
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          prescriptionId = ((PrescriptionHumanized) event.getSelected().toArray()[0]).getId();
          Notification.show(prescriptionId.toString(), Notification.Type.TRAY_NOTIFICATION);
        }
      }
    );
    
    
    setColumns(/*"id", */"doctor", "patient", "prescription", "priority", "expiration");
    setColumnCaptions(/*"id", */"Врач", "Пациент", "Рецепт", "Приоритет", "Срок действия");
  
    PrescriptionDialog prescriptionDialog = new PrescriptionDialog("Рецепт", UI.getCurrent(), prescriptionId, prescriptionRepository);
    Grid.HeaderRow filterRow = grid.appendHeaderRow();
    Grid.HeaderCell patientFilter = filterRow.getCell("patient");
    Grid.HeaderCell priorityFilter = filterRow.getCell("priority");
    Grid.HeaderCell prescriptionFilter = filterRow.getCell("prescription");
    
    //live filter on patient name
    prepareComboBoxFilter(filterPatientComboBox, "Пациент...",
      patientRepository.getAllPatientsFullNames(), patientFilter);
    //live filter on priority name
    prepareComboBoxFilter(filterPriorityComboBox, "Приоритет...",
      Stream.of(Priority.values()).map(Priority::toString).collect(Collectors.toList()), priorityFilter);
    //live filter on prescription text
    prepareTextFieldFilter(filterPrescriptionTextField, prescriptionFilter);
    
    setWidth(100, Sizeable.Unit.PERCENTAGE);
    
    updateList(filterPrescriptionTextField.getValue());
    
    HorizontalLayout buttonsLayout = new HorizontalLayout();
    buttonsLayout.setMargin(true);
    HorizontalLayout controlsLayout = new HorizontalLayout();
    controlsLayout.setSizeFull();
    setMargin(true);
    
    PrescriptionDialog patientForm = new PrescriptionDialog("Врач", UI.getCurrent(), prescriptionId, prescriptionRepository);

//    final Button.ClickListener clickListener = e -> patientForm.open();
//    addButton.addClickListener(clickListener);
//    editButton.addClickListener(clickListener);
//    deleteButton.addClickListener(clickListener);
  
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
  
  private void prepareTextFieldFilter(TextField textField, Grid.HeaderCell filter) {
    grid.deselectAll();
    textField.setImmediate(true);
    textField.setInputPrompt("Рецепт...");
    textField.setWidth("100%");
    textField.addStyleName(ValoTheme.TEXTFIELD_TINY);
    textField.addTextChangeListener(event -> updateList(event.getText()));
    filter.setComponent(filterPrescriptionTextField);
  }
  
  @Override
  public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

//    Notification.show("Prescriptions", Notification.Type.HUMANIZED_MESSAGE);
    updateList(filterPrescriptionTextField.getValue());
    
  }
  
  private void updateList(String prescriptionText) {
    grid.deselectAll();
    Map<String, String> criteria = new HashMap<>();
    criteria.put("patient", (String) filterPatientComboBox.getValue());
    criteria.put("priority", (String) filterPriorityComboBox.getValue());
    criteria.put("prescription", prescriptionText);
    final BeanItemContainer<PrescriptionHumanized> container =
      new BeanItemContainer<>(PrescriptionHumanized.class, prescriptionHumanizedRepository.findByCustomCriteria(PrescriptionHumanized.class, criteria));
    final GeneratedPropertyContainer wrapContainer =
      new GeneratedPropertyContainer(container);//todo>> to add rendered content
    grid.setContainerDataSource(wrapContainer);
//    Notification.show("Данные обновлены.", Notification.Type.TRAY_NOTIFICATION);
  }
  
}
