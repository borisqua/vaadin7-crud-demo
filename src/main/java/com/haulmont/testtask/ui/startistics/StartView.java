package com.haulmont.testtask.ui.startistics;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView
@Title("Haulmont test app / Home")
@Theme("valo")
public class StartView extends VerticalLayout implements View {
  
  public StartView() {

    Label label = new Label("Start view");
    label.setStyleName(ValoTheme.LABEL_HUGE);
    
    setMargin(true);
    addComponents(label);
    setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    
  }
  
  @Override
  public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    Notification.show("Start view. OK", Notification.Type.HUMANIZED_MESSAGE/*TRAY_NOTIFICATION*/);
  }
}
