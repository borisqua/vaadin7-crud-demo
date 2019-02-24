package com.haulmont.testtask.jpa.doctor.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Entity
@Table(name = "all_doctors")
public class DoctorHumanized {
  
  @Id
  @NotNull
  @Column(name = "id")
  private Long id;
  @Column(name = "fullname")
  private String fullname;
  @Column(name = "specialization")
  private String specialization;
  
  public DoctorHumanized() {
  }
  
  public Long getId() {
    return id;
  }
  
  public String getFullname() {
    return fullname;
  }
  
  public String getSpecialization() {
    return specialization;
  }
  
  @Override
  public String toString() {
    return "Doctor {\n " +
      "\tid: " + id + ", \n" +
      "\tfullname: " + fullname + ", \n" +
      "\tspecialization: " + specialization + ", \n" +
      "}";
  }
}
