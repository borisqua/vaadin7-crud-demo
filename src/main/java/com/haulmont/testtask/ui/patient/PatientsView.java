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

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@SpringView
@Title("Haulmont test app / Patients")
@Theme("valo")
public class PatientsView extends VerticalLayout implements View {
  
  private final PatientRepository patientRepository;
  
  private Grid grid = new Grid();
  
  private final Button addButton = new Button("Add");
  private final Button editButton = new Button("Edit");
  private final Button deleteButton = new Button("Delete");
  
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
    
    Grid.Column surnameColumn = grid.getColumn("surname").setHeaderCaption("Фамилия");
    Grid.Column nameColumn = grid.getColumn("name").setHeaderCaption("Имя");
    Grid.Column patronymicColumn = grid.getColumn("patronymic").setHeaderCaption("Отчество");
    Grid.Column phoneColumn = grid.getColumn("phone").setHeaderCaption("Телефон");
    
    setWidth(100, Sizeable.Unit.PERCENTAGE);
    
    updateList();
    
    setMargin(true);
    addComponents(label, grid);
    setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
    
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

