package com.haulmont.testtask.jpa.patient.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Entity
@Table(name = "all_patients")
public class PatientHumanized {
  
  @Id
  @NotNull
  @Column(name = "id")
  private Long id;
  @Column(name = "fullname")
  private String fullname;
  @Column(name = "phone")
  private String phone;
  
  public PatientHumanized() {
  }
  
  public Long getId() {
    return id;
  }
  
  public String getFullname() {
    return fullname;
  }
  
  public String getSpecialization() {
    return phone;
  }
  
  @Override
  public String toString() {
    return ((fullname).trim() + ", телефон: " + phone).trim()+" id:" + id;
  }
}

