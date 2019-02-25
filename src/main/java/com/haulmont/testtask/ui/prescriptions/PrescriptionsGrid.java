package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.jpa.prescription.Prescription;
import com.haulmont.testtask.jpa.prescription.PrescriptionRepository;
import com.haulmont.testtask.jpa.prescription.Priority;
import com.haulmont.testtask.jpa.prescription.view.PrescriptionHumanized;
import com.haulmont.testtask.jpa.prescription.view.PrescriptionHumanizedRepository;
import com.haulmont.testtask.ui.DeleteDialog;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringView
@Title("Haulmont test app / Prescriptions")
@Theme("valo")
public class PrescriptionsGrid extends VerticalLayout implements View {
  
  private Long prescriptionId = -1L;
  private final PrescriptionHumanizedRepository prescriptionHumanizedRepository;
  
  private Grid grid = new Grid();
  
  private final ComboBox filterPatientComboBox = new ComboBox();
  private final ComboBox filterPriorityComboBox = new ComboBox();
  private final TextField filterPrescriptionTextField = new TextField(/*"Pattern to search in prescriptions texts..."*/);
  private final Button editButton = new Button("Edit");
  private final Button deleteButton = new Button("Delete");
  
  private Prescription prescription;
  
  public PrescriptionsGrid(PatientRepository patientRepository,
                           DoctorRepository doctorRepository,
                           PrescriptionRepository prescriptionRepository,
                           PrescriptionHumanizedRepository prescriptionHumanizedRepository) {
    
    this.prescriptionHumanizedRepository = prescriptionHumanizedRepository;
    
    Label label = new Label("Рецепты");
    label.setStyleName(ValoTheme.LABEL_HUGE);
    
    grid.setSizeFull();
    grid.setColumns(/*"id", */"doctor", "patient", "prescription", "priority", "expiration");
    grid.setEditorEnabled(false);
    
    grid.setSelectionMode(Grid.SelectionMode.SINGLE);
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          prescriptionId = ((PrescriptionHumanized) event.getSelected().toArray()[0]).getId();
          prescriptionRepository.findById(prescriptionId).ifPresent(e -> prescription = e);
          Notification.show(prescription.toString(), Notification.Type.TRAY_NOTIFICATION);
          editButton.setEnabled(true);
          deleteButton.setEnabled(true);
        } else {
          editButton.setEnabled(false);
          deleteButton.setEnabled(false);
        }
      }
    );
    
    
    Grid.HeaderRow filterRow = grid.appendHeaderRow();
    Grid.HeaderCell patientFilter = filterRow.getCell("patient");
    Grid.HeaderCell priorityFilter = filterRow.getCell("priority");
    Grid.HeaderCell prescriptionFilter = filterRow.getCell("prescription");
    grid.getColumn("doctor").setHeaderCaption("Врач");
    grid.getColumn("patient").setHeaderCaption("Пациент");
    grid.getColumn("prescription").setHeaderCaption("Рецепт");
    grid.getColumn("priority").setHeaderCaption("Приоритет");
    grid.getColumn("expiration").setHeaderCaption("Срок действия");
    
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
    
    DeleteDialog<Prescription> deleteDialog = new DeleteDialog<>("", UI.getCurrent(), "Удалить выбранную запись?", prescription, prescriptionRepository);

//    Optional<Doctor> doctor = doctorRepository.findById(id);
//    doctor.ifPresent(doctorRepository::delete);
  
    Button addButton = new Button("Add");
    addButton.addClickListener(e -> new PrescriptionDialog("Рецепт", UI.getCurrent(), null, prescriptionRepository, doctorRepository, patientRepository).open());
    editButton.addClickListener(e -> new PrescriptionDialog("Рецепт", UI.getCurrent(), prescription, prescriptionRepository, doctorRepository, patientRepository).open());
    deleteButton.addClickListener(e -> deleteDialog.open());
    
    
    buttonsLayout.addComponents(addButton, editButton, deleteButton);
    controlsLayout.addComponents(label, buttonsLayout);
    addComponents(controlsLayout, grid);
    setComponentAlignment(controlsLayout, Alignment.MIDDLE_CENTER);
    setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
    
    controlsLayout.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
    controlsLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
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
  }
  
  private void updateList(String prescriptionText) {
    grid.deselectAll();
    editButton.setEnabled(false);
    deleteButton.setEnabled(false);
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
