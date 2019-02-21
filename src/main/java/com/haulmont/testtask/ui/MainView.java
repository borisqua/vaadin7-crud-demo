package com.haulmont.testtask.ui;

import com.haulmont.testtask.patient.PatientRepository;
import com.haulmont.testtask.prescription.Priority;
import com.haulmont.testtask.prescription.view.ViewAll;
import com.haulmont.testtask.prescription.view.ViewAllRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@SuppressWarnings("unused")
@SpringUI
@Title("Haulmont test app")
@Theme("valo")
public class MainView extends UI {
  
  private final PatientRepository patientRepository;
  private final ViewAllRepository allPrescriptions;
  
  private Grid grid = new Grid();
  private ComboBox filterPatientComboBox = new ComboBox(/*"Patient..."*/);
  private ComboBox filterPriorityComboBox = new ComboBox(/*"Priority..."*/);
  private TextField filterPrescriptionTextField = new TextField(/*"Search in prescription text..."*/);
  
  @Autowired
  public MainView(PatientRepository patientRepository, ViewAllRepository allPrescriptions) {
    this.patientRepository = patientRepository;
    this.allPrescriptions = allPrescriptions;
  }
  
  protected void init(VaadinRequest vaadinRequest) {
    
    final VerticalLayout layout = new VerticalLayout();
    
    //live filter by patient name
    filterPatientComboBox.setInputPrompt("Пациент");
    filterPatientComboBox.setFilteringMode(FilteringMode.CONTAINS);
    filterPatientComboBox.setInvalidAllowed(false);
    filterPatientComboBox.setNullSelectionAllowed(true);
    filterPatientComboBox.setContainerDataSource(new BeanItemContainer<>(String.class, patientRepository.getAllPatientsFullNames()));
    filterPatientComboBox.addValueChangeListener(event -> updateList(filterPrescriptionTextField.getValue()));
    
    //button to purge the live filter text field
    Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
    clearFilterTextBtn.setDescription("Clear the current filter");
    clearFilterTextBtn.addClickListener(e -> {
      filterPatientComboBox.clear();
      updateList(filterPrescriptionTextField.getValue());
    });
    
    //live filter by priority name
    filterPriorityComboBox.setInputPrompt("Приоритет");
    filterPriorityComboBox.setFilteringMode(FilteringMode.CONTAINS);
    filterPriorityComboBox.setInvalidAllowed(false);
    filterPriorityComboBox.setNullSelectionAllowed(true);
    filterPriorityComboBox.addItems(Priority.UNSOLET.toString(), Priority.CITO.toString(), Priority.STATIM.toString());
    filterPriorityComboBox.addValueChangeListener(event -> updateList(filterPrescriptionTextField.getValue()));
  
    //prescription text filter by change
    filterPrescriptionTextField.addTextChangeListener(event -> {
      updateList(event.getText());
    });
    
    CssLayout filtering = new CssLayout();
    filtering.addComponents(filterPatientComboBox, clearFilterTextBtn, filterPriorityComboBox, filterPrescriptionTextField);
    filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    
    grid.setColumns(/*"id", */"doctor", "patient", "prescription", "priority", "expiration");
    grid.setSizeFull();
    
    layout.addComponents(filtering, grid);
    layout.setExpandRatio(grid, 1);
    
    updateList(filterPrescriptionTextField.getValue());
    
    layout.setMargin(true);
    setContent(layout);
  }
  
  private void updateList(String prescriptionText) {
    if (String.valueOf(filterPatientComboBox.getValue()).trim().length() > 0) {
        grid.setContainerDataSource(
          new BeanItemContainer<>(ViewAll.class, allPrescriptions.findByCustomCriteria(
            (String) filterPatientComboBox.getValue(),   //patient name pattern
            (String) filterPriorityComboBox.getValue(),  //priority name pattern
            prescriptionText)
          ));
    } else {
      grid.setContainerDataSource(
        new BeanItemContainer<>(ViewAll.class, (List<ViewAll>) allPrescriptions.findAll()));
    }
  }
  
  @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
  @VaadinServletConfiguration(ui = MainView.class, productionMode = false)
  public static class MyUIServlet extends VaadinServlet {
  }
}
