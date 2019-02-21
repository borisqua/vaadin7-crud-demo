package com.haulmont.testtask.prescription.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Entity
@Table(name = "all_prescriptions")
public class ViewAll {
  
  @Id
  @NotNull
  @Column(name = "id")
  private Long id;
  @Column(name = "doctor")
  private String doctor;
  @Column(name = "patient")
  private String patient;
  @Column(name="prescription")
  private String prescription;
  @Column(name = "priority")
  private String priority;
  @Column(name = "expiration")
  private Date expiration;
  
  public ViewAll() {
  }
  
  public Long getId() {
    return id;
  }
  
  public String getDoctor() {
    return doctor;
  }
  
  public String getPatient() {
    return patient;
  }
  
  public String getPrescription() {
    return prescription;
  }
  
  public String getPriority() {
    return priority;
  }
  
  public Date getExpiration() {
    return expiration;
  }
  
  @Override
  public String toString() {
    return "Prescription from All Prescriptions View {\n " +
      "\tid: " + id + ", \n" +
      "\tdoctor: " + doctor + ", \n" +
      "\tpatient: " + patient + ", \n" +
      "\tprescription: " + prescription + ", \n" +
      "\tpriority: " + priority + ", \n" +
      "\texpiration: " + expiration + "\n" +
      "}";
  }
}
