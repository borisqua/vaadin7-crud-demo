package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.jpa.prescription.Prescription;
import com.haulmont.testtask.jpa.prescription.PrescriptionRepository;
import com.haulmont.testtask.jpa.prescription.Priority;
import com.haulmont.testtask.jpa.prescription.view.PrescriptionHumanized;
import com.haulmont.testtask.jpa.prescription.view.PrescriptionHumanizedRepository;
import com.haulmont.testtask.ui.ModalDialog;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("FieldCanBeLocal")
@SpringView
@Title("Haulmont test app / Рецепты")
@Theme("valo")
public class PrescriptionsGrid extends VerticalLayout implements View {
  
  private final PrescriptionHumanizedRepository prescriptionHumanizedRepository;
  
  private final ComboBox filterPatientComboBox = new ComboBox();
  private final ComboBox filterPriorityComboBox = new ComboBox();
  private final TextField filterPrescriptionTextField = new TextField(/*"Pattern to search in prescriptions texts..."*/);
  
  private final Button addButton = new Button("Add");
  private final Button editButton = new Button("Edit");
  private final Button deleteButton = new Button("Delete");
  
  private final DoctorRepository doctorRepository;
  private final PatientRepository patientRepository;
  private final PrescriptionRepository prescriptionRepository;
  
  private Long prescriptionId = -1L;
  private Grid grid = new Grid();
  private ModalDialog editDialog;
  private DeletePrescriptionDialog deleteDialog;
  private Prescription prescription;
  
  public PrescriptionsGrid(PatientRepository patientRepository,
                           DoctorRepository doctorRepository,
                           PrescriptionRepository prescriptionRepository,
                           PrescriptionHumanizedRepository prescriptionHumanizedRepository) {
    
    this.doctorRepository = doctorRepository;
    this.patientRepository = patientRepository;
    this.prescriptionRepository = prescriptionRepository;
    
    this.prescriptionHumanizedRepository = prescriptionHumanizedRepository;
    
    setSizeFull();
    
//    editDialog = new EditPrescriptionDialog("Рецепт", UI.getCurrent(), this,
//      "Изменение данных", null, this.prescriptionRepository, this.doctorRepository, this.patientRepository);
//    deleteDialog = new DeletePrescriptionDialog("", UI.getCurrent(), this,
//      "Удалить выбранную запись?", null, this.prescriptionRepository);
    
    Label label = new Label("Рецепты");
    label.setStyleName(ValoTheme.LABEL_HUGE);
    
    grid.setSizeFull();
    grid.setHeight(15, Unit.CM);
    grid.setColumns(/*"id", */"doctor", "patient", "prescription", "priority", "expiration");
    grid.setEditorEnabled(false);
    grid.setHeightByRows(10);
    
    grid.setSelectionMode(Grid.SelectionMode.SINGLE);
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
//          this.prescriptionId = ((PrescriptionHumanized) event.getSelected().toArray()[0]).getId();
//          this.prescriptionRepository.findById(prescriptionId).ifPresent(p -> this.prescription = p);
//          Notification.show(this.prescription.toString().getSelectedRows(), Notification.Type.TRAY_NOTIFICATION);
//          deleteDialog = new DeletePrescriptionDialog("", UI.getCurrent(), this, "Удалить выбранную запись?",
//            this.prescription, this.prescriptionRepository);
//          editDialog = new EditPrescriptionDialog("Рецепт", UI.getCurrent(), this, "Изменение данных",
//            this.prescription, this.prescriptionRepository, this.doctorRepository, this.patientRepository);
          editButton.setEnabled(true);
          deleteButton.setEnabled(true);
        } else {
          this.prescriptionId = -1L;
//          deleteDialog = new DeletePrescriptionDialog("", UI.getCurrent(), this, "",
//            null, null);
//          editDialog = new EditPrescriptionDialog("Рецепт", UI.getCurrent(), this, "",
//            null, this.prescriptionRepository, this.doctorRepository, this.patientRepository);
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
      patientRepository.getAllPatientsFullName(), patientFilter);
    //live filter on priority name
    prepareComboBoxFilter(filterPriorityComboBox, "Приоритет...",
      Stream.of(Priority.values()).map(Priority::toString).collect(Collectors.toList()), priorityFilter);
    //live filter on prescription text
    prepareTextFieldFilter(filterPrescriptionTextField, prescriptionFilter);
    
    setWidth(100, Unit.PERCENTAGE);
    setHeight(100, Unit.PERCENTAGE);
    
    updateList(filterPrescriptionTextField.getValue());
    
    HorizontalLayout buttonsLayout = new HorizontalLayout();
    buttonsLayout.setMargin(true);
    HorizontalLayout controlsLayout = new HorizontalLayout();
    controlsLayout.setSizeFull();
    setMargin(true);
    
    
    addButton.addClickListener(e -> {
      grid.deselectAll();
      editDialog = new EditPrescriptionDialog("Рецепт", UI.getCurrent(), this, "Добавить запись",
        null, this.prescriptionRepository, this.doctorRepository, this.patientRepository);
      editDialog.open();
    });
    editButton.addClickListener(e -> {
      this.prescriptionId = ((PrescriptionHumanized) (grid.getSelectionModel()).getSelectedRows().toArray()[0]).getId();
      this.prescriptionRepository.findById(prescriptionId).ifPresent(p -> this.prescription = p);
      editDialog = new EditPrescriptionDialog("Рецепт", UI.getCurrent(), this, "Изменить запись",
        this.prescription, this.prescriptionRepository, this.doctorRepository, this.patientRepository);
      editDialog.open();
    });
    deleteButton.addClickListener(e -> {
      this.prescriptionId = ((PrescriptionHumanized) (grid.getSelectionModel()).getSelectedRows().toArray()[0]).getId();
      this.prescriptionRepository.findById(prescriptionId).ifPresent(p -> this.prescription = p);
      deleteDialog = new DeletePrescriptionDialog("", UI.getCurrent(), this, "Удалить выбранную запись?",
        this.prescription, this.prescriptionRepository);
      deleteDialog.open();
    });
    
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
  
  String getPrescriptionTextFilter() {
    return this.filterPrescriptionTextField.getValue();
  }
  
  void updateList(String prescriptionText) {
    grid.deselectAll();
    editButton.setEnabled(false);
    deleteButton.setEnabled(false);
    Map<String, String> criteria = new HashMap<>();
    criteria.put("patient", (String) filterPatientComboBox.getValue());
    criteria.put("priority", (String) filterPriorityComboBox.getValue());
    criteria.put("prescription", prescriptionText);
    final BeanItemContainer<PrescriptionHumanized> container =
      new BeanItemContainer<>(PrescriptionHumanized.class, prescriptionHumanizedRepository.findByCustomCriteria(PrescriptionHumanized.class, criteria));
    grid.setContainerDataSource(container);
//    Notification.show("Данные обновлены.", Notification.Type.TRAY_NOTIFICATION);
  }
  
}
