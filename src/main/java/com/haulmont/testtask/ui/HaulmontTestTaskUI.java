package com.haulmont.testtask.ui;

import com.haulmont.testtask.jpa.doctor.DoctorRepository;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.haulmont.testtask.jpa.prescription.view.ViewAllRepository;
import com.haulmont.testtask.ui.doctor.DoctorsView;
import com.haulmont.testtask.ui.patient.PatientsView;
import com.haulmont.testtask.ui.prescriptions.PrescriptionsView;
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

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@SpringUI
@Title("Haulmont test app")
@Theme("valo")
public class HaulmontTestTaskUI extends UI {
  
  private Navigator navigator;
  
  private final String START = "start";
  private final String PRESCRIPTIONS = "prescriptions";
  private final String DOCTORS = "doctors";
  private final String PATIENTS = "patients";
  
  private VerticalLayout content;
  
  private final ViewAllRepository allPrescriptions;
  private final DoctorRepository doctorRepository;
  private final PatientRepository patientRepository;
  
  @Autowired
  public HaulmontTestTaskUI(PatientRepository patientRepository,
                            ViewAllRepository allPrescriptions,
                            DoctorRepository doctorRepository) {
    this.patientRepository = patientRepository;
    this.allPrescriptions = allPrescriptions;
    this.doctorRepository = doctorRepository;
  }
  
  @Override
  protected void init(VaadinRequest vaadinRequest) {
    
    final VerticalLayout layout = new VerticalLayout();
    
    content = new VerticalLayout();
//    content.setSizeFull();//todo?? what if run without it
    
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

//    addStyleName(ValoTheme.UI_WITH_MENU);//todo?? what if run without it
//    setResponsive(true);//todo?? what if run without it
    
    layout.addComponents(menuBar, content);
    
    layout.setComponentAlignment(menuBar, Alignment.TOP_CENTER);
    
    navigator = new Navigator(this, content);
    navigator.addView(START, new StartView());
    navigator.addView(PRESCRIPTIONS, new PrescriptionsView(patientRepository, allPrescriptions));
    navigator.addView(DOCTORS, new DoctorsView(doctorRepository));
    navigator.addView(PATIENTS, new PatientsView(patientRepository));
    
    setContent(layout);
    navigator.navigateTo(START);
  }

//  @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
//  @VaadinServletConfiguration(ui = MainView.class, productionMode = false)
//  public static class MyUIServlet extends VaadinServlet {
//  }
}
