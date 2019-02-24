package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.jpa.patient.Patient;
import com.haulmont.testtask.jpa.patient.PatientRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

@SpringView
@Title("Haulmont test app / Patients")
@Theme("valo")
public class PatientsView extends VerticalLayout implements View {
  
  private final PatientRepository patientRepository;
  
  private Grid grid = new Grid();
  
  private Patient patient;
  
  public PatientsView(PatientRepository patientRepository) {
    
    this.patientRepository = patientRepository;
  
    Label label = new Label("Пациенты");
    label.setStyleName(ValoTheme.LABEL_HUGE);
    
    grid.setSizeFull();
    grid.setColumns(/*"id", */"surname", "name", "patronymic", "phone");
    grid.setEditorEnabled(false);
    
    grid.setSelectionMode(Grid.SelectionMode.SINGLE);
    grid.addSelectionListener(event -> {
        if (event.getSelected().size() > 0) {
          patient = (Patient) event.getSelected().toArray()[0];
          Notification.show(patient.toString(), Notification.Type.TRAY_NOTIFICATION);
        }
      }
    );
    
    grid.getColumn("surname").setHeaderCaption("Фамилия");
    grid.getColumn("name").setHeaderCaption("Имя");
    grid.getColumn("patronymic").setHeaderCaption("Отчество");
    grid.getColumn("phone").setHeaderCaption("Телефон");
    
    setWidth(100, Sizeable.Unit.PERCENTAGE);
    
    updateList();
  
    HorizontalLayout buttonsLayout = new HorizontalLayout();
    buttonsLayout.setMargin(true);
    HorizontalLayout controlsLayout = new HorizontalLayout();
    controlsLayout.setSizeFull();
    setMargin(true);
    
    PatientForm  patientForm = new PatientForm("Врач", UI.getCurrent(), patient, patientRepository);
  
    final Button.ClickListener clickListener = e -> patientForm.open();
  
    Button addButton = new Button("Add");
    addButton.addClickListener(clickListener);
  
    Button editButton = new Button("Edit");
    editButton.addClickListener(clickListener);
  
    Button deleteButton = new Button("Delete");
  
    buttonsLayout.addComponents(addButton, editButton, deleteButton);
    controlsLayout.addComponents(label, buttonsLayout);
    addComponents(controlsLayout, grid);
    setComponentAlignment(controlsLayout, Alignment.MIDDLE_CENTER);
    setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
  
    controlsLayout.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
    controlsLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
  
  }
  
  @Override
  public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
//    Notification.show("Patients", Notification.Type.ASSISTIVE_NOTIFICATION);
    updateList();
  }
  
  private void updateList() {
    grid.deselectAll();
    final BeanItemContainer<Patient> container = new BeanItemContainer<>(Patient.class, (List<Patient>) patientRepository.findAll());
    final GeneratedPropertyContainer wrapContainer = new GeneratedPropertyContainer(container);//todo>> to add rendered content
    grid.setContainerDataSource(wrapContainer);
//    Notification.show("Данные обновлены.", Notification.Type.TRAY_NOTIFICATION);
  }
  
}

