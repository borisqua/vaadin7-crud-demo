package com.haulmont.testtask.ui;

import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.jpa.doctor.view.DoctorHumanizedRepository;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.jpa.patient.view.PatientHumanizedRepository;
import com.haulmont.testtask.jpa.prescription.PrescriptionRepository;
import com.haulmont.testtask.jpa.prescription.view.PrescriptionHumanizedRepository;
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
  private final DoctorHumanizedRepository doctorHumanizedRepository;
  private final PatientRepository patientRepository;
  private final PatientHumanizedRepository patientHumanizedRepository;
  private final PrescriptionRepository prescriptionRepository;
  private final PrescriptionHumanizedRepository prescriptionHumanizedRepository;
  
  @Autowired
  public HaulmontTestTaskUI(DoctorRepository doctorRepository,
                            DoctorHumanizedRepository doctorHumanizedRepository,
                            PatientRepository patientRepository,
                            PatientHumanizedRepository patientHumanizedRepository,
                            PrescriptionRepository prescriptionRepository,
                            PrescriptionHumanizedRepository prescriptionHumanizedRepository
  ) {
    this.doctorRepository = doctorRepository;
    this.doctorHumanizedRepository = doctorHumanizedRepository;
    this.patientRepository = patientRepository;
    this.patientHumanizedRepository = patientHumanizedRepository;
    this.prescriptionRepository = prescriptionRepository;
    this.prescriptionHumanizedRepository = prescriptionHumanizedRepository;
  }
  
  @Override
  protected void init(VaadinRequest vaadinRequest) {
    
    final VerticalLayout layout = new VerticalLayout();
    
    VerticalLayout content = new VerticalLayout();
    
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
    navigator.addView(START, new StartView());
    navigator.addView(PRESCRIPTIONS, new PrescriptionsGrid(patientRepository, prescriptionRepository, prescriptionHumanizedRepository));
    navigator.addView(DOCTORS, new DoctorsGrid(doctorHumanizedRepository, doctorRepository));
    navigator.addView(PATIENTS, new PatientsGrid(patientHumanizedRepository, patientRepository));
    
    setContent(layout);
    navigator.navigateTo(START);
  }

//  @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
//  @VaadinServletConfiguration(ui = MainView.class, productionMode = false)
//  public static class MyUIServlet extends VaadinServlet {
//  }
}