package com.haulmont.testtask.jpa.stats;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings({"unused"})
@Entity
@Table(name="doctors_charts")
public class DoctorResult {
  
  @Id
  @NotNull
  @Column(name="id")
  private Long id;
  
  @Column(name="doctor")
  private String doctor;
  
  @Column(name="number")
  private Integer number;
  
  @Column(name="chart")
  private String chart;
  
  DoctorResult(){}
  
  public Long getId() {
    return id;
  }
  
  public String getDoctor() {
    return doctor;
  }
  
  public Integer getNumber() {
    return number;
  }
  
  public String getChart() {
    return chart;
  }
  
  @Override
  public String toString(){
    return doctor + " " + chart;
  }
}
