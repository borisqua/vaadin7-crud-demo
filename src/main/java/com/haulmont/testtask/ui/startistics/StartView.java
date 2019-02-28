package com.haulmont.testtask.ui.startistics;

import com.haulmont.testtask.jpa.stats.DoctorResult;
import com.haulmont.testtask.jpa.stats.DoctorResultsRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@SpringView
@Title("Haulmont test app / Главная страница")
@Theme("valo")
public class StartView extends VerticalLayout implements View {
  
  final private Grid grid;
  final private DoctorResultsRepository doctorResultsRepository;
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  public StartView(DoctorResultsRepository doctorResultsRepository) {
    
    this.doctorResultsRepository = doctorResultsRepository;
    setMargin(true);
    setSpacing(true);
    
    setWidth(100, Sizeable.Unit.PERCENTAGE);
    
    Label title = new Label("Количество выписанных рецептов по врачам");
    Label blank = new Label(" ");
    Label comment = new Label("Данные обновляются автоматически");
    
    title.setStyleName(ValoTheme.LABEL_HUGE);
    blank.setStyleName(ValoTheme.LABEL_HUGE);
    comment.setStyleName(ValoTheme.BUTTON_SMALL);
    UI ui = UI.getCurrent();
    
    this.grid = new Grid();
    
    this.grid.setSizeFull();
    this.grid.setEditorEnabled(false);
    this.grid.setSelectionMode(Grid.SelectionMode.NONE);
    this.grid.setStyleName(ValoTheme.TEXTFIELD_HUGE);
    this.grid.setColumns("doctor", "chart");
    this.grid.getColumn("doctor").setWidth(300);
    this.grid.removeHeaderRow(0);
    final BeanItemContainer<DoctorResult> container =
      new BeanItemContainer<>(DoctorResult.class, (List<DoctorResult>) this.doctorResultsRepository.findAll());
    this.grid.setContainerDataSource(container);
    this.grid.setHeight(15, Unit.CM);
    this.grid.setHeightByRows(10);
    this.grid.setEnabled(false);
    
    addComponents(title, comment, blank, this.grid);
    setComponentAlignment(this.grid, Alignment.MIDDLE_CENTER);
    
    ui.setPollInterval(2000);
    ui.addPollListener(e -> {
      LOGGER.info("HaulmontLog4j2: Vaadin PollListener event.");
      this.grid.setContainerDataSource(
        new BeanItemContainer<>(DoctorResult.class, (List<DoctorResult>) this.doctorResultsRepository.findAll()));
    });
    
  }
  
  @Override
  public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
//    Notification.show("Start view. OK", Notification.Type.HUMANIZED_MESSAGE/*TRAY_NOTIFICATION*/);
  }
}
