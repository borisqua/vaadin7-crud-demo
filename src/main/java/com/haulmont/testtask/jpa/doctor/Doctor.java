package com.haulmont.testtask.jpa.doctor;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name="doctors")
public class Doctor {
  
  @Id
  @GeneratedValue//(strategy = GenerationType.TABLE)
  @Column(name="id")
  private Long id;
  @Column(name="name")
  private String name;
  @Column(name="surname")
  private String surname;
  @Column(name="patronymic")
  private String patronymic;
  @Column(name="specialization")
  private String specialization;
  
  public Doctor(){}
  public Doctor(String name, String surname){
    
    this(name, surname, "", "");
    
  }
  public Doctor(String name, String surname, String specialization){
    
    this(name, surname, "", specialization);
    
  }
  public Doctor(String name, String surname, String patronymic, String specialization) {
    
    this.name = name;
    this.surname = surname;
    this.patronymic = patronymic;
    this.specialization = specialization;
    
  }
  
  public Long getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getSurname() {
    return surname;
  }
  
  public void setSurname(String surname) {
    this.surname = surname;
  }
  
  public String getPatronymic() {
    return patronymic;
  }
  
  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
  }
  
  public String getSpecialization() {
    return specialization;
  }
  
  public void setSpecialization(String specialization) {
    this.specialization = specialization;
  }
  
  @Override
  public String toString(){
    return ((name.trim() + " " + patronymic).trim() + " " + surname).trim();
  }
}
