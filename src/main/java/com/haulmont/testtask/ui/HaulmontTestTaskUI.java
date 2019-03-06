package com.haulmont.testtask.ui;

import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.jpa.prescription.PrescriptionRepository;
import com.haulmont.testtask.jpa.prescription.view.PrescriptionHumanizedRepository;
import com.haulmont.testtask.jpa.stats.DoctorResultsRepository;
import com.haulmont.testtask.ui.doctor.DoctorsGrid;
import com.haulmont.testtask.ui.patient.PatientsGrid;
import com.haulmont.testtask.ui.prescriptions.PrescriptionsGrid;
import com.haulmont.testtask.ui.startistics.StartView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Title("Haulmont test app")
@Theme("valo")
public class HaulmontTestTaskUI extends UI {
  
  private Navigator navigator;
  
  private final String START = "start";
  private final String PRESCRIPTIONS = "prescriptions";
  private final String DOCTORS = "doctors";
  private final String PATIENTS = "patients";
  
  private final DoctorRepository doctorRepository;
  private final PatientRepository patientRepository;
  private final PrescriptionRepository prescriptionRepository;
  private final PrescriptionHumanizedRepository prescriptionHumanizedRepository;
  private final DoctorResultsRepository doctorResultsRepository;
  
  @Autowired
  public HaulmontTestTaskUI( DoctorRepository doctorRepository,
                            PatientRepository patientRepository,
                            PrescriptionRepository prescriptionRepository,
                            PrescriptionHumanizedRepository prescriptionHumanizedRepository,
                            DoctorResultsRepository doctorResultsRepository) {
    
    this.doctorRepository = doctorRepository;
    this.patientRepository = patientRepository;
    this.prescriptionRepository = prescriptionRepository;
    this.prescriptionHumanizedRepository = prescriptionHumanizedRepository;
    this.doctorResultsRepository = doctorResultsRepository;
  }
  
  @Override
  protected void init(VaadinRequest vaadinRequest) {
    
    setSizeFull();
    
    final VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);
    
    VerticalLayout content = new VerticalLayout();
    content.setSizeFull();
    
    MenuBar menuBar = new MenuBar();
    menuBar.setWidth(100, Unit.PERCENTAGE);
    menuBar.setAutoOpen(true);
    menuBar.addItem("", FontAwesome.HOME,
      item -> navigator.navigateTo(START));
    menuBar.addItem("Рецепты", /*FontAwesome.FILE_TEXT*/null,
      item -> navigator.navigateTo(PRESCRIPTIONS));
    menuBar.addItem("Врачи", /*FontAwesome.MEDKIT*/null,
      item -> navigator.navigateTo(DOCTORS));
    menuBar.addItem("Пациенты", /*FontAwesome.MALE*/null,
      item -> navigator.navigateTo(PATIENTS));
    
    layout.addComponents(menuBar, content);
    
    layout.setComponentAlignment(menuBar, Alignment.TOP_CENTER);
    
    navigator = new Navigator(this, content);
    navigator.addView(START, new StartView(doctorResultsRepository));
    navigator.addView(PRESCRIPTIONS, new PrescriptionsGrid(patientRepository, doctorRepository, prescriptionRepository, prescriptionHumanizedRepository));
    navigator.addView(DOCTORS, new DoctorsGrid(doctorRepository));
    navigator.addView(PATIENTS, new PatientsGrid(patientRepository));
    
    setContent(layout);
    navigator.navigateTo(START);
  }

//  @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
//  @VaadinServletConfiguration(ui = HaulmontTestTaskUI.class, productionMode = true)
//  public static class MyUIServlet extends VaadinServlet {
//  }
}
