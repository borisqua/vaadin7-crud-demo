package com.haulmont.testtask.ui;

import com.haulmont.testtask.patient.PatientRepository;
import com.haulmont.testtask.prescription.view.ViewAll;
import com.haulmont.testtask.prescription.view.ViewAllRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
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
  private ComboBox filterPatientComboBox = new ComboBox("Patient...");
  private ComboBox filterPriority = new ComboBox("Priority...");
  private TextField filterPrescription = new TextField("Search in prescription text...");
  
  @Autowired
  public MainView(PatientRepository patientRepository, ViewAllRepository allPrescriptions) {
    this.patientRepository = patientRepository;
    this.allPrescriptions = allPrescriptions;
  }
  
  protected void init(VaadinRequest vaadinRequest) {
    
    final VerticalLayout layout = new VerticalLayout();
    
    //live filter by name
    filterPatientComboBox.setInputPrompt("Filter by patient's name...");
    filterPatientComboBox.setInvalidAllowed(false);
    filterPatientComboBox.setNullSelectionAllowed(true);
    filterPatientComboBox.setInputPrompt("Start name entering...");
    filterPatientComboBox.setContainerDataSource(new BeanItemContainer<>(String.class, patientRepository.getAllPatientsNames()));
    filterPatientComboBox.addValueChangeListener(event -> // Java 8
      grid.setContainerDataSource(new BeanItemContainer<>(
        ViewAll.class, allPrescriptions.findByCustomCriteria((String) filterPatientComboBox.getValue(), null, null))));
    filterPatientComboBox.getValue();
    
    //button to purge the live filter text field
    Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
    clearFilterTextBtn.setDescription("Clear the current filter");
    clearFilterTextBtn.addClickListener(e -> {
      filterPatientComboBox.clear();
      updateList();
    });
    
    CssLayout filtering = new CssLayout();
    filtering.addComponents(filterPatientComboBox, clearFilterTextBtn);
    filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    
    grid.setColumns(/*"id", */"doctor", "patient", "prescription", "priority", "expiration");
    grid.setSizeFull();
    
    layout.addComponents(filtering, grid);
    layout.setExpandRatio(grid, 1);
    
    updateList();
    
    layout.setMargin(true);
    setContent(layout);
  }
  
  private void updateList() {
    if (String.valueOf(filterPatientComboBox.getValue()).trim().length() > 0) {
      grid.setContainerDataSource(
        new BeanItemContainer<>(ViewAll.class, allPrescriptions.findByCustomCriteria((String) filterPatientComboBox.getValue(), null, null)));
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
