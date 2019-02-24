package com.haulmont.testtask.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class GridForm<T> extends VerticalLayout implements View {
  
  protected CrudRepository<T, Long> repository;
  protected T entity;
  protected Grid grid = new Grid();
  protected final Button addButton = new Button("Add");
  protected final Button editButton = new Button("Edit");
  protected final Button deleteButton = new Button("Delete");
  
  public GridForm(Class<T> clazz, String gridCaption, CrudRepository<T, Long> repository){
    
    this.repository = repository;
  
    Label label = new Label(gridCaption);
    label.setStyleName(ValoTheme.LABEL_HUGE);
  
    grid.setSizeFull();
    grid.setEditorEnabled(false);
    grid.setSelectionMode(Grid.SelectionMode.SINGLE);
  
    setWidth(100, Sizeable.Unit.PERCENTAGE);
  
    updateList(clazz);
  
    HorizontalLayout buttonsLayout = new HorizontalLayout();
    buttonsLayout.setMargin(true);
    HorizontalLayout controlsLayout = new HorizontalLayout();
    controlsLayout.setSizeFull();
    setMargin(true);
    
    buttonsLayout.addComponents(addButton, editButton, deleteButton);
    controlsLayout.addComponents(label, buttonsLayout);
    addComponents(controlsLayout, grid);
    setComponentAlignment(controlsLayout, Alignment.MIDDLE_CENTER);
    setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
  
    controlsLayout.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
    controlsLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
  
  }
  
  protected void setColumns(String... columns){
    grid.setColumns((Object[]) columns);
  }
  
  protected void setColumnCaptions(String... captions) {
    final List<Grid.Column> columns = grid.getColumns();
    final Iterator<Grid.Column> columnIterator = columns.iterator();
    final Iterator<String> captionIterator = Stream.of(captions).iterator();
  
    while (columnIterator.hasNext() && captionIterator.hasNext()) {
      columnIterator.next().setHeaderCaption(captionIterator.next());
    }
    
  }
  
  @Override
  public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
//    Notification.show("Данные обновлены.", Notification.Type.TRAY_NOTIFICATION);
  }
  
  private void updateList(Class<T> clazz) {
    grid.deselectAll();
    final BeanItemContainer<T> container = new BeanItemContainer<>(clazz, (List<T>) repository.findAll());
    final GeneratedPropertyContainer wrapContainer = new GeneratedPropertyContainer(container);//todo>> to add possible rendered content
    grid.setContainerDataSource(wrapContainer);
//    Notification.show("Данные обновлены.", Notification.Type.TRAY_NOTIFICATION);
  }
  
}
